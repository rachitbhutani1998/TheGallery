package com.example.myapplication.ui.main

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.ImageModel
import com.example.myapplication.ui.adapter.ImageListAdapter
import com.example.myapplication.util.ImageHolderCallback
import com.example.myapplication.util.buildUrl
import com.example.myapplication.util.gone
import com.example.myapplication.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*


@AndroidEntryPoint
class ImageListFragment : Fragment(), TextWatcher, TextView.OnEditorActionListener,
    ImageHolderCallback {

    companion object {
        fun newInstance() = ImageListFragment()

        const val IMAGE_LIST = "image_list"
    }

    private lateinit var viewModel: MainViewModel


    private var mAdapter: ImageListAdapter? = null
    private var isLoading = false

    private var loadingAnim: ValueAnimator? = null
    private var currentPage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        setupTempView()

        ib_search.setOnClickListener { searchImages() }

        et_search.addTextChangedListener(this)
        et_search.setOnEditorActionListener(this)

        setupListObserver(viewModel.getImages())
        setupPageObserver(viewModel.getPageNumber())
    }

    private fun searchImages() {
        isLoading = true
        viewModel.getImages(et_search.text.toString())
        setupTempView()
        hideKeyboard()
    }

    private fun setupRecyclerView() {
        mAdapter = ImageListAdapter(requireContext(), this)
        rv_images.adapter = mAdapter
        rv_images.layoutManager = GridLayoutManager(context, 3)
        rv_images.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(RecyclerView.VERTICAL)
                        .not() && mAdapter?.isEmpty() == false
                )
                    searchImages()
            }
        })
    }

    private fun setupTempView() {
        when {
            isLoading -> showLoadingView()
            mAdapter?.itemCount ?: 0 <= 0 -> showEmptyView()
            else -> {
                tv_temp_view.gone()
                loadingAnim?.cancel()
            }
        }
    }

    private fun showEmptyView() {
        tv_temp_view.show()
        tv_temp_view.text = getString(R.string.empty_placeholder)
        loadingAnim?.cancel()
    }

    private fun showLoadingView() {
        tv_temp_view.show()
        tv_temp_view.text = getString(R.string.loading_placeholder)
        startLoadingAnimation()
    }

    private fun setupListObserver(imageLiveData: LiveData<MutableList<ImageModel>>) {
        imageLiveData.observe(viewLifecycleOwner) { images ->
            isLoading = false
            val clearAdapter = currentPage == 1
            if (clearAdapter) rv_images.smoothScrollToPosition(0)
            mAdapter?.addToBottom(images, clearAdapter)
            setupTempView()
        }
    }

    private fun setupPageObserver(pageNo: LiveData<Int>) {
        pageNo.observe(viewLifecycleOwner) {
            currentPage = it
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //Not Needed
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //Not Needed
    }

    override fun afterTextChanged(s: Editable?) {
        ib_search.visibility = if (et_search.text.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_SEARCH && et_search?.text?.isNotEmpty() == true) {
            searchImages()
            true
        } else false
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(et_search.windowToken, 0)
    }

    private fun startLoadingAnimation() {
        loadingAnim = ValueAnimator.ofFloat(0f, -50f).apply {
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animator ->
                tv_temp_view?.translationY = animator.animatedValue as Float
            }
        }
        loadingAnim?.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(IMAGE_LIST, mAdapter?.getImages())
    }

    override fun onImageLoadFailed(pos: Int) {
        mAdapter?.removeImage(pos)
    }

    override fun onImageClicked(image: ImageModel) {
        openImageFragment(image)
    }

    private fun openImageFragment(model: ImageModel) {
        val imageViewFragment = ImageViewFragment.newInstance(model.buildUrl())
        imageViewFragment.show(childFragmentManager, "")
    }
}
