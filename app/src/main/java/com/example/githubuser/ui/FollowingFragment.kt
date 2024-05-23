package com.example.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.response.FollowResponseItem
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentFollowingBinding

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
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val username = arguments?.getString(FollowersFragment.ARG_USERNAME)
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)

        binding.rvFollowing.layoutManager = layoutManager
        binding.rvFollowing.addItemDecoration(itemDecoration)
        getFollowing(username!!)

        return binding.root
    }

    private fun getFollowing(name: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(name)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserFollowing(responseBody)
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

    private fun setUserFollowing(followers: List<FollowResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(followers)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Following Fragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}