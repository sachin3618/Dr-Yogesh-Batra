package com.example.dryogeshbatra.commonViewModel

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.dryogeshbatra.LoginActivity
import com.example.dryogeshbatra.RegisterActivity
import com.example.dryogeshbatra.firestore.FirestoreClass
import com.example.dryogeshbatra.utils.Constants
import com.example.shopiz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson


class BookViewModel: ViewModel(){
   // val bookList = MutableLiveData<ArrayList<BooksList>>()
    val mFireStore = FirebaseFirestore.getInstance()


    fun downloadPdf(url: String, chapterIndex: Int, activity: Activity){
        var myDownloadId: Long = 0
        var request: DownloadManager.Request = DownloadManager.Request(
            Uri.parse(url))
            .setTitle("Book Chapter $chapterIndex")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS,"Chapter"+chapterIndex+".pdf")
            .setAllowedOverMetered(true)

        var dm =  activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)

        var brodcastReciever = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if(id == myDownloadId){
                    Toast.makeText(activity, "Chapter Downloaded Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }

        activity.registerReceiver(brodcastReciever, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }






/*
    fun fetchBookDetails() {
        val productsList: ArrayList<BooksList> = ArrayList()
        mFireStore.collection(Constants.BookDetails)
            .get()
            .addOnSuccessListener { document ->
                for (i in document.documents) {
                    val book = i.toObject(BooksList::class.java)
                    productsList.add(book!!)
                }
                Log.i("documentValue", productsList.toString())
                bookList.postValue(productsList)

            }

    }*/


    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun checkIfTheUserIsLoggedIn(activity : Activity): String{

        val sharedPrefers =
            activity.getSharedPreferences(
                Constants.LOGGED_USER_DETAILS,
                MODE_PRIVATE
            )
        val gson = Gson()
        val json: String? = sharedPrefers.getString(Constants.LOGGED_STRING_KEY, "")

        if (json == "") {
            return ""
            //password has not been saved...
        } else {
            //password is already there...
            val userDetails = gson.fromJson(json, User::class.java)
            return userDetails.id
        }
        // Create an instance of the editor which is help us to edit the SharedPreference.
    }
/*

    fun uploadBookDetails(bookDetails: BooksList){
        mFireStore.collection(Constants.BookDetails)
            .document(Constants.BooksWithPdf)
            .set(bookDetails, SetOptions.merge())
            .addOnSuccessListener {
                Log.i("BookUploaded", "Yes")
            }
            .addOnFailureListener{
                Log.i("BookUploadedFailed", it.toString())
            }

    }

    fun fetchBookList() : ArrayList<BooksList>{
        val productsList: ArrayList<BooksList> = ArrayList()
        mFireStore.collection(Constants.BookDetails)
            .get()
            .addOnSuccessListener { document ->
                for(i in document.documents){
                    val book = i.toObject(BooksList::class.java)
                    productsList.add(book!!)
                }
                Log.i("documentValue", productsList.toString())
            }
        return productsList
    }

*/

    fun getUserDetails(activity: Activity) {
        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(FirestoreClass.getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                // Here we have received the document snapshot which is converted into the User Data model object.
                val user = document.toObject(User::class.java)!!

               /* val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYSHOPPAL_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                // Create an instance of the editor which is help us to edit the SharedPreference.
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    user.id
                )
                editor.apply()*/

                when (activity) {
                    is LoginActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userLoggedInSuccess(user)
                    }

                    /*is SettingsActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userDetailsSuccess(user)
                    }*/
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    /*is SettingsActivity -> {
                        activity.hideProgressDialog()
                    }*/
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    /*   fun getProductsList(fragment : Fragment){
           mFireStore.collection(Constants.PRODUCTS)
               .whereEqualTo(Constants.USER_ID, getCurrentUserID())
               .get()
               .addOnSuccessListener { document ->
                       Log.e("Products List", document.documents.toString())
                   val productsList: ArrayList<Product> = ArrayList()
                   for(i in document.documents){
                       val product = i.toObject(Product::class.java)
                       product!!.product_id = i.id
                       productsList.add(product)
                   }

                   when(fragment){
                       is ProductsFragment ->{
                           fragment.successProductsListFromFireStore(productsList)
                       }
                   }
               }
       }

       fun getDashboardItemsList(fragment: DashboardFragment){
           // The collection name for PRODUCTS
           mFireStore.collection(Constants.PRODUCTS)
               .get() // Will get the documents snapshots.
               .addOnSuccessListener { document ->

                   // Here we get the list of boards in the form of documents.
                   Log.e(fragment.javaClass.simpleName, document.documents.toString())

                   // Here we have created a new instance for Products ArrayList.
                   val productsList: ArrayList<Product> = ArrayList()

                   // A for loop as per the list of documents to convert them into Products ArrayList.
                   for (i in document.documents) {

                       val product = i.toObject(Product::class.java)!!
                       product.product_id = i.id
                       productsList.add(product)
                   }

                   // Pass the success result to the base fragment.
                   fragment.successDashboardItemsList(productsList)
               }
               .addOnFailureListener { e ->
                   // Hide the progress dialog if there is any error which getting the dashboard items list.
                   fragment.hideProgressDialog()
                   Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
               }
       }


       fun deleteProduct(fragment: ProductsFragment, productId: String) {

           mFireStore.collection(Constants.PRODUCTS)
               .document(productId)
               .delete()
               .addOnSuccessListener {

                   // Notify the success result to the base class.
                   fragment.productDeleteSuccess()
               }
               .addOnFailureListener { e ->

                   // Hide the progress dialog if there is an error.
                   fragment.hideProgressDialog()

                   Log.e(
                       fragment.requireActivity().javaClass.simpleName,
                       "Error while deleting the product.",
                       e
                   )
               }
       }
   */

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }





}