package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.RecyclerViewAdapter
import com.ivanzaytsev.db.appDB
import kotlinx.android.synthetic.main.fragment_db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DbFragment : Fragment() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_db, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        navController = NavHostFragment.findNavController(this)
        imageHome.setOnClickListener {
            navController.popBackStack()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val database = appDB.getInstance(requireContext())
            val projects = database.projectDao().getAll()
            withContext(Dispatchers.Main) {
                recyclerView.adapter = RecyclerViewAdapter(projects, resources)

            }
        }


    }

}