package com.example.dryogeshbatra.fragments.userChat

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dryogeshbatra.DashboardActivity
import com.example.dryogeshbatra.R
import com.example.dryogeshbatra.databinding.FragmentUserChatBinding
import com.example.dryogeshbatra.firestore.FirestoreClass
import com.example.dryogeshbatra.models.UserData.User
import com.example.dryogeshbatra.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn.hasPermissions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration
import kotlinx.android.synthetic.main.fragment_user_chat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


const val APP_ID = "3d98e0dff5a54e3fa536527d2b60668d"

class UserChatFragment : Fragment() {



    // private var channelName: String? = null
    val viewModel: UserChatViewModel by activityViewModels()


    // private var mRtcEngine: RtcEngine? = null
    lateinit var binding: FragmentUserChatBinding
    lateinit var mUserDetails: User
    private var mRtcEngine: RtcEngine? = null
    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        /**
         * Occurs when a remote user (Communication)/ host (Live Broadcast) joins the channel.
         * This callback is triggered in either of the following scenarios:
         *
         * A remote user/host joins the channel by calling the joinChannel method.
         * A remote user switches the user role to the host by calling the setClientRole method after joining the channel.
         * A remote user/host rejoins the channel after a network interruption.
         * The host injects an online media stream into the channel by calling the addInjectStreamUrl method.
         *
         * @param uid User ID of the remote user sending the video streams.
         * @param elapsed Time elapsed (ms) from the local user calling the joinChannel method until this callback is triggered.
         */
        override fun onUserJoined(uid: Int, elapsed: Int) {
            requireActivity().runOnUiThread { setupRemoteVideo(uid) }
        }

        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         *     Leave the channel: When the user/host leaves the channel, the user/host sends a
         *     goodbye message. When this message is received, the SDK determines that the
         *     user/host leaves the channel.
         *
         *     Drop offline: When no data packet of the user or host is received for a certain
         *     period of time (20 seconds for the communication profile, and more for the live
         *     broadcast profile), the SDK assumes that the user/host drops offline. A poor
         *     network connection may lead to false detections, so we recommend using the
         *     Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who leaves the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         *     USER_OFFLINE_QUIT(0): The user left the current channel.
         *     USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         *     USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        override fun onUserOffline(uid: Int, reason: Int) {
            requireActivity().runOnUiThread { onRemoteUserLeft() }
        }

        /**
         * Occurs when a remote user stops/resumes sending the video stream.
         *
         * @param uid ID of the remote user.
         * @param muted Whether the remote user's video stream playback pauses/resumes:
         * true: Pause.
         * false: Resume.
         */
        override fun onUserMuteVideo(uid: Int, muted: Boolean) {
            requireActivity().runOnUiThread { onRemoteUserVideoMuted(uid, muted) }
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                Log.i("PermissionAccess", "Granted")
                lifecycleScope.launch{
                    displayCameraFragment()
                }
            }
        }

    private fun takePhoto() {
        activity?.let {
            if (hasPermissions(activity as Context, PERMISSIONS)) {
                lifecycleScope.launch{
                    displayCameraFragment()
                }
            } else {
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private suspend fun displayCameraFragment() {
                val job1 = lifecycleScope.async{
                    viewModel.FetchRestrauntDetails(mUserDetails.id)
                    Log.i("fahdfgh", "tokenfetched")
                }

                val job2 = lifecycleScope.async {
                    viewModel.userId.postValue(mUserDetails.id)
                    Log.i("fahdfgh", "idfetched")

                }

                val job3 = lifecycleScope.async {
                    initAgoraEngineAndJoinChannel()
                    Log.i("fahdfgh", "agoraInitialized")
                }

                job1.await()
                job2.await()
                job3.await()
        Log.i("PermissionAccess", "Granted")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserChatBinding.inflate(layoutInflater)

        val sharedPref = requireActivity().getSharedPreferences(
            Constants.LOGGED_USER_DETAILS,
            AppCompatActivity.MODE_PRIVATE
        )

        val gson = Gson()
        val json: String? = sharedPref.getString(Constants.LOGGED_STRING_KEY, "")
        mUserDetails = gson.fromJson(json, User::class.java)
        return binding.root
    }






    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
      {



         if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(
                 Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
             initAgoraEngineAndJoinChannel()
         }
     }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(
                Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel()
        }*/

        takePhoto()



        call_ended.setOnClickListener {
            onEncCallClicked()
        }

        switch_camera.setOnClickListener {
            onSwitchCameraClicked()
        }

        mic_mute.setOnClickListener {
            onLocalAudioMuteClicked()
        }

        camera_off.setOnClickListener {
            onLocalVideoMuteClicked()
        }


    }

    private fun initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine()
        setupVideoProfile()
        setupLocalVideo()
        joinChannel()
    }

    /* private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
         Log.i(LOG_TAG, "checkSelfPermission $permission $requestCode")
         if (ContextCompat.checkSelfPermission(requireActivity(),
                 permission) != PackageManager.PERMISSION_GRANTED) {

             ActivityCompat.requestPermissions(requireActivity(),
                 arrayOf(permission),
                 requestCode)
             return false
         }
         return true
     }*/

    /*override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode)

        when (requestCode) {
            PERMISSION_REQ_ID_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO)
                    requireActivity().onBackPressed()
                }
            }
            PERMISSION_REQ_ID_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel()
                } else {
                    showLongToast("No permission for " + Manifest.permission.CAMERA)
                    requireActivity().onBackPressed()
                }
            }
        }
    }*/

    private fun showLongToast(msg: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireActivity().applicationContext,
                msg,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        leaveChannel()
        /*
          Destroys the RtcEngine instance and releases all resources used by the Agora SDK.

          This method is useful for apps that occasionally make voice or video calls,
          to free up resources for other operations when not making calls.
         */
        RtcEngine.destroy()
        mRtcEngine = null
    }

    fun onLocalVideoMuteClicked() {
        try {
            val iv = binding.cameraOff
            if (iv.isSelected) {
                iv.isSelected = false
                iv.clearColorFilter()
            } else {
                iv.isSelected = true
                iv.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)
            }

            // Stops/Resumes sending the local video stream.
            mRtcEngine!!.muteLocalVideoStream(iv.isSelected)

            val surfaceView = binding.localVideoViewContainer.getChildAt(0) as SurfaceView
            surfaceView.setZOrderMediaOverlay(!iv.isSelected)
            surfaceView.visibility = if (iv.isSelected) View.GONE else View.VISIBLE
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()
        }

    }

    fun onLocalAudioMuteClicked() {

        try {
            val iv = binding.micMute
            if (iv.isSelected) {
                iv.isSelected = false
                iv.clearColorFilter()
            } else {
                iv.isSelected = true
                iv.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)
            }

            // Stops/Resumes sending the local audio stream.
            mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()
        }


    }

    fun onSwitchCameraClicked() {
        try {
            mRtcEngine!!.switchCamera()

        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()

        }
        // Switches between front and rear cameras.
    }

    fun onEncCallClicked() {
        requireActivity().finish()
    }

    private fun initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(
                requireActivity().baseContext,
                getString(R.string.agora_app_id),
                mRtcEventHandler
            )
        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))

            throw RuntimeException("NEED TO  rtc sdk init fatal error\n" + Log.getStackTraceString(e))
        }
    }

    private fun setupVideoProfile() {
        try {
            mRtcEngine!!.enableVideo()
//      mRtcEngine!!.setVideoProfile(Constants.VIDEO_PROFILE_360P, false) // Earlier than 2.3.0

            // Please go to this page for detailed explanation
            // https://docs.agora.io/en/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_rtc_engine.html#af5f4de754e2c1f493096641c5c5c1d8f
            mRtcEngine!!.setVideoEncoderConfiguration(
                VideoEncoderConfiguration(
                    VideoEncoderConfiguration.VD_640x360,
                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                    VideoEncoderConfiguration.STANDARD_BITRATE,
                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
                )
            )
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()

        }
        // In simple use cases, we only need to enable video capturing
        // and rendering once at the initialization step.
        // Note: audio recording and playing is enabled by default.

    }

    private fun setupLocalVideo() {

        try{
            val surfaceView = RtcEngine.CreateRendererView(requireActivity().baseContext)
            surfaceView.setZOrderMediaOverlay(true)
            binding.localVideoViewContainer.addView(surfaceView)
            // Initializes the local video view.
            // RENDER_MODE_FIT: Uniformly scale the video until one of its dimension fits the boundary. Areas that are not filled due to the disparity in the aspect ratio are filled with black.
            mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()

        }
        // This is used to set a local preview.
        // The steps setting local and remote view are very similar.
        // But note that if the local user do not have a uid or do
        // not care what the uid is, he can set his uid as ZERO.
        // Our server will assign one and return the uid via the event
        // handler callback function (onJoinChannelSuccess) after
        // joining the channel successfully.

    }

    private fun joinChannel() {
        // 1. Users can only see each other after they join the
        // same channel successfully using the same app id.
        // 2. One token is only valid for the channel name that
        // you use to generate this token.
       // Log.i("tokenn", viewModel.networkToken.value.toString())
        try {
            viewModel.networkToken.observe(viewLifecycleOwner, Observer {
                mRtcEngine!!.joinChannel(
                    it,
                    viewModel.userId.value,
                    "Extra Optional Data",
                    0
                )
            })
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()

        }

        // if you do not specify the uid, we will generate the uid for you
    }

    private fun setupRemoteVideo(uid: Int) {
        // Only one remote video view is available for this
        // tutorial. Here we check if there exists a surface
        // view tagged as this uid.
        try {
            val container = binding.remoteVideoViewContainer
            if (container.childCount >= 1) {
                return
            }

            /*
              Creates the video renderer view.
              CreateRendererView returns the SurfaceView type. The operation and layout of the view
              are managed by the app, and the Agora SDK renders the view provided by the app.
              The video display view must be created using this method instead of directly
              calling SurfaceView.
             */
            val surfaceView = RtcEngine.CreateRendererView(requireActivity().baseContext)
            container.addView(surfaceView)
            // Initializes the video view of a remote user.
            mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))

            surfaceView.tag = uid // for mark purpose
            binding.quickTipsWhenUseAgoraSdk.visibility = View.GONE
        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()

        }

    }

    private fun leaveChannel() {
        try {
            mRtcEngine!!.leaveChannel()

        }catch (e: Exception){
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT).show()

        }
    }

    private fun onRemoteUserLeft() {
        binding.remoteVideoViewContainer.removeAllViews()

        binding.quickTipsWhenUseAgoraSdk.visibility = View.VISIBLE
    }

    private fun onRemoteUserVideoMuted(uid: Int, muted: Boolean) {

        val surfaceView = binding.remoteVideoViewContainer.getChildAt(0) as SurfaceView

        val tag = surfaceView.tag
        if (tag != null && tag as Int == uid) {
            surfaceView.visibility = if (muted) View.GONE else View.VISIBLE
        }
    }

    companion object {

        private val LOG_TAG = DashboardActivity::class.java.simpleName

        var PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )

        private const val PERMISSION_REQ_ID_RECORD_AUDIO = 22
        private const val PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1
    }
}