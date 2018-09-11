package com.muzadev.dicodingkotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muzadev.dicodingkotlin.R
import com.muzadev.dicodingkotlin.activity.EventDetailActivity
import com.muzadev.dicodingkotlin.adapter.EventAdapter
import com.muzadev.dicodingkotlin.model.Event
import com.muzadev.dicodingkotlin.presenter.EventView
import com.muzadev.dicodingkotlin.presenter.Presenter
import kotlinx.android.synthetic.main.fragment_common_layout.*
import kotlinx.android.synthetic.main.fragment_common_layout.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by zulfakar on 07/09/18.
 * For educational purposes
 */
class NextMatchFragment : Fragment(), EventView {
    private lateinit var presenter: Presenter<EventView>
    private var eventList: MutableList<Event> = mutableListOf()
    private lateinit var adapter: EventAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val leagueId = "4328"

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = Presenter(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_common_layout, container, false)
        adapter = EventAdapter(activity!!.applicationContext, eventList) {
            startActivity<EventDetailActivity>("eventId" to it.idEvent)
        }
        recyclerView = view.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getNextMatch(leagueId)
        }

        presenter.getNextMatch(leagueId)
        return view
    }

    override fun showEventList(events: List<Event>) {
        eventList.clear()
        eventList.addAll(events)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }


}