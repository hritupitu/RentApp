package com.android.example.housingconnect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_form_image.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormImageFragment : Fragment() {

    val mAuth = FirebaseAuth.getInstance()

    var imageUri: Uri? = null

    lateinit var post:Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = mAuth.currentUser
        Log.d("User",mAuth.currentUser.email)
        // TODO: PHASE 6.1 - Get an instance of the singleton housingService defined in the MainActivity

        val args : FormImageFragmentArgs by navArgs()
        post=args.postVal

        // TODO: PHASE 6.1 - set an OnClickListener on the upload Image Button and start an implicit
        //  intent that will pick an image from the users gallery. Make sure to match the request code
        //  used in onActivityResult (which is 2020)

        uploadImageButton.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI),2020)
        }


        // TODO: PHASE 3.2 - when a user clicks 'done' navigate the user to the HousingFeedFragment
        //  we will do more than this in a future phase. In PHASE 6.2 - instead of navigating in the
        //  onClickListener, call postImage() and only navigate when you successfully upload the
        //  image and data onto the server.

        submitBtnToHome.setOnClickListener {

            //TODO: FINISH this
            post.email = user!!.email
            post.image = ""
            postImage()
//            val action = FormImageFragmentDirections.actionFormImageFragmentToHousingFeedFragment()
//            findNavController().navigate(action)
        }



    }

    fun postImage() {
        if (imageUri == null) {
            return
        }

        // TODO: PHASE 6.2 - Use a content resolver to read image data and extension from the resource URI

        val contentResolver = requireContext().contentResolver
        val bytes = contentResolver.openInputStream(imageUri!!)!!.readBytes()
        val extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(imageUri!!))

        // TODO: PHASE 6.2 - Create a Multipart Body that contains the image data and type

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),bytes)
        val part = MultipartBody.Part.createFormData("image","image.$extension",requestBody)

        // TODO: PHASE 6.2 - Call the image upload endpoint from your housingService API and when the
        //  image is successfully uploaded save the returned path to the image into the post object
        //  to send to the server.
        val housingService = (requireActivity() as MainActivity).housingService
        housingService.uploadImage(part).enqueue(object : retrofit2.Callback<ImageUploadResponse>{
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                Log.d("ImageUpload","Success in onResponse")
                post.image = response.body()?.path
                housingService.createPost(post).enqueue(object : retrofit2.Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        Log.d("PostCreate","Success in onResponse")
                        val action = FormImageFragmentDirections.actionFormImageFragmentToHousingFeedFragment()
                        findNavController().navigate(action)
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.d("PostCreate","Success in onFailure")
                        Log.d("PostCreate",t.toString())
                    }

                })
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                Log.d("ImageUpload","Success in onFailure")
                Log.d("ImageUpload",t.toString())
            }

        })

        // TODO: PHASE 7 - Then make use the housing endpoint to upload the housing listing
        //  onto the server (with a path to the image and everything else as well).


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2020) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data!!.data
                housingImage.setImageURI(imageUri)
            }
        }
    }
}