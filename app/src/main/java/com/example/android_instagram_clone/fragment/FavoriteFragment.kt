package com.example.android_instagram_clone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_instagram_clone.R
import com.example.android_instagram_clone.activity.MainActivity
import com.example.android_instagram_clone.adapter.FavoriteAdapter
import com.example.android_instagram_clone.adapter.HomeAdapter
import com.example.android_instagram_clone.manager.AuthManager
import com.example.android_instagram_clone.manager.DatabaseManager
import com.example.android_instagram_clone.manager.handler.DBPostHandler
import com.example.android_instagram_clone.manager.handler.DBPostsHandler
import com.example.android_instagram_clone.model.Post
import com.example.android_instagram_clone.utils.Utils
import java.lang.Exception

open class FavoriteFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity, 1))

        loadLikedFeeds()

    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavoriteAdapter(this, items)
        recyclerView.adapter = adapter
    }

    fun likeOrUnlikePost(post: Post) {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)

        loadLikedFeeds()
    }

    fun loadLikedFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadLikedFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    fun showDeleteDialog(post: Post) {
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object :
            Utils.DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if (isChosen) {
                    deletePost(post)
                }
            }
        })
    }

    fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                loadLikedFeeds()
            }

            override fun onError(e: Exception) {

            }
        })
    }
}