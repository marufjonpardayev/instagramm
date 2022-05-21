package com.example.instagramm.fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramm.R
import com.example.instagramm.adapter.HomeAdapter
import com.example.instagramm.databinding.FragmentHomeBinding
import com.example.instagramm.extension.viewBinding
import com.example.instagramm.manager.AuthManager
import com.example.instagramm.manager.DatabaseManager
import com.example.instagramm.manager.handler.DBPostsHandler
import com.example.instagramm.model.Post
import com.example.instagramm.utils.BounceEdgeEffectFactory
import java.lang.Exception
import java.lang.RuntimeException


class HomeFragment : BaseFragment() {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    var listener: HomeListener? = null
    lateinit var adapter: HomeAdapter
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size > 0) {
            loadMyFeeds()
        }
    }

    /*
    onAttach is for communication of fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = if (context is HomeListener) {
            context
        } else {
            throw RuntimeException("$context must implement HomeListener")
        }
    }

    /*
    onDetach is for communication of fragments
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initView() {
        binding.apply {
            ivCamera.setOnClickListener {
                listener!!.scrollToUpload()
            }

            rvHome.edgeEffectFactory = BounceEdgeEffectFactory()
        }

        loadMyFeeds()
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    private fun refreshAdapter(posts: ArrayList<Post>) {
        adapter = HomeAdapter(posts)
        binding.rvHome.adapter = adapter
    }


    /*
    This interface is created for communication with UploadFragment
     */
    interface HomeListener {
        fun scrollToUpload()
    }
}