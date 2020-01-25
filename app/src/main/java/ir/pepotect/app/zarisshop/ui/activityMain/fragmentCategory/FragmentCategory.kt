package ir.pepotect.app.zarisshop.ui.activityMain.fragmentCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotect.app.zarisshop.model.dataModel.CategoryModel
import ir.pepotect.app.zarisshop.presenter.CategoryPresenter
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress

class FragmentCategory : MyFragment() {

    private lateinit var progress: DialogProgress

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        RecyclerView(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = DialogProgress(ctx)
        getCategorys()
    }

    private fun getCategorys() {
        progress.show()
        CategoryPresenter(object : CategoryPresenter.CategoryResult {
            override fun result(ok: Boolean, message: String, data: ArrayList<CategoryModel>?) {
                progress.cancel()
                if (ok)
                    setUpRV(data)
                else
                    toast(message)
            }
        }, cancelTag).getCategorys()
    }

    private fun setUpRV(data: java.util.ArrayList<CategoryModel>?) {
        (view as RecyclerView).apply {
            adapter = AdapterCategory(data!!)
            layoutManager = GridLayoutManager(ctx, 2, RecyclerView.VERTICAL, false)
        }
    }

}