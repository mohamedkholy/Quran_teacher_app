package com.alda3ia.ES.quranlearn.ui

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Environment
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.databinding.FragmentVideoBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment
import com.alda3ia.ES.quranlearn.model.VideoItem
import com.alda3ia.ES.quranlearn.util.Downloader
import java.io.File



class VideoFragment : BaseFragment<FragmentVideoBinding>(R.layout.fragment_video) {
    override val binder: (View) -> FragmentVideoBinding
        get() = FragmentVideoBinding::bind


    private lateinit var onDownloadComplete:BroadcastReceiver
    private val args:VideoFragmentArgs by navArgs()
    private lateinit var item: VideoItem
    private lateinit var mediaController: MediaController
    private var downloadID:Long=0
    private lateinit var path:String
    private var isDownloaded=false
    val permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it)
            downloadVideo()
        else
            Toast.makeText(requireContext(),getString(R.string.Permission_required),Toast.LENGTH_SHORT).show()
    }

    val permissionLauncher2=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it) {
            binding.videoPlayer.setVideoURI(path.toUri())
            StartingVideo()
        }

        else
            Toast.makeText(requireContext(),getString(R.string.Permission_required),Toast.LENGTH_SHORT).show()
    }



    override fun setup() {
        path="${Environment.getExternalStorageDirectory()}/${Environment.DIRECTORY_DOWNLOADS}/${args.title}.mp4"
        item=args.item
        binding.translationText.text=getStringResourceByName(item.name)
        binding.arabicText.text=item.aya
        mediaController= MediaController(requireContext()).apply { setAnchorView(binding.videoPlayer) }
        binding.videoPlayer.setMediaController(mediaController)

        binding.title.apply {
            when(args.title){
                "Pronounciation key"->{
                    this.text=getString(R.string.keyButtonText)
                    binding.arabicText.visibility=View.GONE
                    binding.view.visibility=View.GONE
                }
                "Al-Istiadha"->{
                    binding.note.visibility=View.VISIBLE
                    this.text=args.title
                }
                else->this.text=args.title
            }
        }

        if(File(path).exists()) {
           disableDownloadButton()
           isDownloaded=true
        }

    }

    override fun addCallbacks() {


        binding.videoPlayer.setOnErrorListener(object:MediaPlayer.OnErrorListener{
            override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
                showAlertDialog(
                    getString(R.string.cant_play),
                    getString(R.string.cant_play_message),
                    null
                )
                return true
            }
        })

        binding.backButton.setOnClickListener {
            mediaController.hide()
            requireView().findNavController().popBackStack()
        }

        binding.playButton.setOnClickListener {
            binding.videoPlayer.apply {
                if(!isDownloaded)
                {  if(isOnline()){
                    setVideoPath(item.url)
                    StartingVideo()
                }
                    else
                    showNoInternetDialog()
                }
                else{
                    if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                        setVideoURI(path.toUri())
                        StartingVideo()
                    }
                    else if(  shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                         showAlertDialog(getString(R.string.Permission_required),context.getString(R.string.permission_required_downloaded_text),permissionLauncher2)
                    }
                    else
                    {
                        permissionLauncher2.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }

                }


            }

        }

        binding.whatsupButton.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=+20 1110371247"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = url.toUri()
            startActivity(i)
        }

        binding.messengerButton.setOnClickListener {
            val url="https://m.me/bassem2442"
            startActivity(Intent(Intent.ACTION_VIEW,url.toUri()))
        }

        binding.downloadButton.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                 downloadVideo()
            }
            else if(  shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showAlertDialog(getString(R.string.Permission_required),getString(R.string.permission_required_download_text),permissionLauncher)
            else
            {
                permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        binding.videoPlayer.setOnPreparedListener {
            binding.progressBar.visibility=View.GONE
        }

       onDownloadComplete =object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val downloadManager=requireActivity().getSystemService(DownloadManager::class.java)
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                val cursor=downloadManager.query(DownloadManager.Query().setFilterById(id))
                val columIndex=cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                if(cursor.moveToFirst()&&cursor.getInt(columIndex)==DownloadManager.STATUS_SUCCESSFUL){
                    Toast.makeText(requireContext(), getString(R.string.Download_Completed), Toast.LENGTH_SHORT)
                        .show()
                    isDownloaded=true
                    starVideo(path)
                    disableDownloadButton()
                }
                else{
                    binding.downloadButton.text=getText(R.string.down_button_text)
                    Toast.makeText(requireContext(),getString(R.string.download_faild),Toast.LENGTH_SHORT).show()
                }
                }
            }


        requireActivity().registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }


    private fun showAlertDialog(title: String, message: String, permissionLauncher2: ActivityResultLauncher<String>?) {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(true)
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                permissionLauncher2?.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.create().show()
    }

    private fun downloadVideo() {
        if (isOnline()) {
            if (binding.downloadButton.text != getString(R.string.Downloading_text)) {
                binding.downloadButton.text = requireActivity().getString(R.string.Downloading_text)
                downloadID = Downloader(requireContext()).dowload(item.url.toString(), args.title)
            }
        } else {
           showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        showAlertDialog(getString(R.string.no_internet),getString(R.string.no_iternet_message),null)
    }

    private fun StartingVideo() {
        binding.videoPlayer.start()
        binding.playButton.visibility=View.INVISIBLE
        binding.progressBar.visibility=View.VISIBLE
    }

    private fun starVideo(path: String) {
        binding.videoPlayer.setVideoURI(path.toUri())
        binding.playButton.visibility=View.INVISIBLE
        binding.videoPlayer.start()

    }

    private fun getStringResourceByName(aString: String): String? {
        val packageName: String = requireActivity().packageName
        val resId = resources.getIdentifier(aString, "string", packageName)
        return requireActivity().resources.getString(resId)
    }

    private fun disableDownloadButton() {
        binding.downloadButton.apply {
            text=context.getString(R.string.downloaded)
            isEnabled=false
            binding.downloadButton.backgroundTintList=requireActivity().resources.getColorStateList(R.color.gray)
        }
    }


    fun isOnline(): Boolean {
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(onDownloadComplete)
    }


}