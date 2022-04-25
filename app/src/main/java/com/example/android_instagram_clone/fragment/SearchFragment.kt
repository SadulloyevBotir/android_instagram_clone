package com.example.android_instagram_clone.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_instagram_clone.R
import com.example.android_instagram_clone.adapter.SearchAdapter
import com.example.android_instagram_clone.manager.AuthManager
import com.example.android_instagram_clone.manager.DatabaseManager
import com.example.android_instagram_clone.manager.handler.DBFollowHandler
import com.example.android_instagram_clone.manager.handler.DBUserHandler
import com.example.android_instagram_clone.manager.handler.DBUsersHandler
import com.example.android_instagram_clone.model.User
import com.example.android_instagram_clone.utils.Utils
import java.lang.Exception

/**
 * In SearchFragment, all registered users can be found by searching keyword and followed
 */
open class SearchFragment : BaseFragment() {
    val TAG = SearchFragment::class.java.simpleName
    lateinit var rv_search: RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fagment_search, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        rv_search = view.findViewById(R.id.rv_search)
        rv_search.setLayoutManager(GridLayoutManager(activity, 1))
        val et_search = view.findViewById<EditText>(R.id.et_search)
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = s.toString().trim()
                usersByKeyword(keyword)
            }
        })
        loadUsers()
        refreshAdapter(items)
    }

    private fun refreshAdapter(items: ArrayList<User>) {
        val adapter = SearchAdapter(this, items)
        rv_search.adapter = adapter
    }


        private fun usersByKeyword(keyword: String) {
            if (keyword.isEmpty())
                refreshAdapter(items)

            users.clear()
            for (user in items)
                if (user.fullname.toLowerCase().startsWith(keyword.toLowerCase()))
                    users.add(user)

            refreshAdapter(users)
        }

    private fun loadUsers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
                    override fun onSuccess(following: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid, users, following))
                        refreshAdapter(items)
                    }

                    override fun onError(e: Exception) {

                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun mergedUsers(
        uid: String,
        users: ArrayList<User>,
        following: ArrayList<User>
    ): ArrayList<User> {
        val items = ArrayList<User>()
        for (u in users) {
            val user = u
            for (f in following) {
                if (u.uid == f.uid) {
                    user.isFollowed = true
                    break
                }
            }
            if (uid != user.uid) {
                items.add(user)
            }
        }
        return items
    }


    fun followOrUnfollow(to: User) {
        val uid = AuthManager.currentUser()!!.uid
        if (!to.isFollowed) {
            followUser(uid, to)
        } else {
            unFollowUser(uid, to)
        }
    }

    private fun followUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.followUser(me!!, to, object : DBFollowHandler {
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = isFollowed
                        DatabaseManager.storePostsToMyFeed(uid, to)
                        Utils.sendNotification(
                            requireContext(),
                            to.device_token,
                            getString(R.string.app_name),
                            getString(R.string.str_followed_notification).replace("$", me.fullname)
                        )
                    }

                    override fun onError(e: Exception) {
                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun unFollowUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.unFollowUser(me!!, to, object : DBFollowHandler {
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = false
                        DatabaseManager.removePostsFromMyFeed(uid, to)
                    }

                    override fun onError(e: Exception) {

                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

}