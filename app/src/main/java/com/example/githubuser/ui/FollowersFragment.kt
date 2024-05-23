package com.example.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.*
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentFollowersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val username = arguments?.getString(ARG_USERNAME)
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)

        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.addItemDecoration(itemDecoration)
        getFollower(username!!)

        return binding.root
    }


    private fun getFollower(name: String) {
        Log.d(TAG, "okehh masuk")
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowers(name)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserFollowers(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t}")
            }
        })
    }

    private fun setUserFollowers(followers: List<FollowResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(followers)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        var ARG_USERNAME: String? = ""
        var ARG_POSITION: String? = ""
        private const val TAG = "Followers Fragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}