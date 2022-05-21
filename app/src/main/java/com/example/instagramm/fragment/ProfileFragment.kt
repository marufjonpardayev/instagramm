package com.example.instagramm.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.instagramm.R
import com.example.instagramm.activity.SignInActivity
import com.example.instagramm.adapter.ProfileAdapter
import com.example.instagramm.databinding.FragmentProfileBinding
import com.example.instagramm.extension.viewBinding
import com.example.instagramm.manager.AuthManager
import com.example.instagramm.manager.DatabaseManager
import com.example.instagramm.manager.StorageManager
import com.example.instagramm.manager.handler.DBPostsHandler
import com.example.instagramm.manager.handler.DBUserHandler
import com.example.instagramm.manager.handler.DBUsersHandler
import com.example.instagramm.manager.handler.StorageHandler
import com.example.instagramm.model.Post
import com.example.instagramm.model.User
import com.example.instagramm.utils.BounceEdgeEffectFactory
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import java.lang.Exception


class ProfileFragment : BaseFragment() {

    private val binding by viewBinding { FragmentProfileBinding.bind(it) }
    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()
    val TAG = ProfileFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun loadFollowing() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                binding.tvFollowing.text = users.size.toString()
            }

            override fun onError(e: Exception) {}
        })
    }

    private fun loadMyFollowers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowers(uid, object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                binding.tvFollowers.text = users.size.toString()
            }

            override fun onError(e: Exception) {}
        })
    }

    private fun initViews() {
        binding.apply {
            ivLogout.setOnClickListener {
                AuthManager.signOut()
                callSignInActivity()
            }

            ivProfile.setOnClickListener {
                pickFishBunPhoto()
            }

            rvProfile.edgeEffectFactory = BounceEdgeEffectFactory()
        }

        loadUserInfo()
        loadMyPosts()

        loadFollowing()
        loadMyFollowers()
    }

    private fun loadMyPosts() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                binding.tvPostNumber.text = posts.size.toString()
                d(TAG, "onSuccess: $posts")
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun callSignInActivity() {
        startActivity(Intent(requireContext(), SignInActivity::class.java))
    }

    private fun loadUserInfo() {
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                if (user != null) {
                    showUserInfo(user)
                }
            }

            override fun onError(e: Exception) {}
        })
    }

    private fun showUserInfo(user: User) {
        binding.apply {
            tvFullname.text = user.fullname
            tvEmail.text = user.email
            Glide.with(requireContext())
                .load(user.userImg)
                .placeholder(R.drawable.img_default_user)
                .error(R.drawable.img_default_user)
                .into(ivProfile)
        }
    }

    private fun refreshAdapter(posts: java.util.ArrayList<Post>) {
        binding.rvProfile.adapter = ProfileAdapter(this, posts)
    }

    private fun pickFishBunPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
            pickedPhoto = allPhotos[0]
            uploadUserPhoto()
        }

    private fun uploadUserPhoto() {
        if (pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                DatabaseManager.updateUserImg(imgUrl)
                binding.ivProfile.setImageURI(pickedPhoto)
            }

            override fun onError(exception: Exception?) {}
        })
    }

    private fun loadPosts(): ArrayList<Post> {
        val items = ArrayList<Post>()

        return items
    }
}