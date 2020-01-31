package ir.pepotect.app.zarisshop.ui.activityMain.fragmentHome

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import cn.lightsky.infiniteindicator.IndicatorConfiguration
import cn.lightsky.infiniteindicator.InfiniteIndicator
import cn.lightsky.infiniteindicator.OnPageClickListener
import cn.lightsky.infiniteindicator.Page
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.ProductList
import ir.pepotect.app.zarisshop.model.dataModel.TagList
import ir.pepotect.app.zarisshop.model.dataModel.TagTable
import ir.pepotect.app.zarisshop.model.dataModel.TimingProductList
import ir.pepotect.app.zarisshop.presenter.HomeListPresenter
import ir.pepotect.app.zarisshop.ui.activityMain.ActivityMain
import ir.pepotect.app.zarisshop.ui.uses.GlideLoader
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.list_product.view.*
import kotlinx.android.synthetic.main.list_timing_product.view.*
import kotlinx.android.synthetic.main.table_tag.view.*
import java.util.*
import kotlin.collections.ArrayList

class FragmentHome : MyFragment(), OnPageClickListener, HomeListPresenter.Result {

    private val pageViews = ArrayList<Page>()
    lateinit var progress: DialogProgress

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = DialogProgress(ctx)
        init()
    }

    private fun init() {
        setUpSlideShow()
        getHomeData()
    }

    private fun getHomeData() {
        progress.show()
        HomeListPresenter(this, cancelTag).getHomeData()
    }

    private fun setUpSlideShow() {
        initSlideShowBounds()
        initSlideShowData()

        val configuration = IndicatorConfiguration.Builder()
            .imageLoader(GlideLoader())
            .isStopWhileTouch(true)
            .onPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {

                }
            })
            .onPageClickListener(this)
            .direction(InfiniteIndicator.ALIGN_LEFT)
            .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
            .build()
        (SlideShow as InfiniteIndicator).apply {
            init(configuration)
            notifyDataChange(pageViews)
        }
    }

    private fun initSlideShowBounds() {
        val p = Point()
        (ctx as ActivityMain).windowManager.defaultDisplay.getRealSize(p)
        (SlideShow as InfiniteIndicator).apply {
            layoutParams.height = (p.x / 1.7).toInt()
        }
    }

    private fun initSlideShowData() {
        pageViews.add(
            Page(
                "A",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000018501.jpg",
                this
            )
        )
        pageViews.add(
            Page(
                "B",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000018426.jpg",
                this
            )
        )
        pageViews.add(
            Page(
                "C",
                "https://dkstatics-public.digikala.com/digikala-products/115306464.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60",
                this
            )
        )
        pageViews.add(
            Page(
                "D",
                "https://dkstatics-public.digikala.com/digikala-products/115196704.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60",
                this
            )
        )

    }

    override fun onPageClick(position: Int, page: Page?) {
    }

    override fun tagTable(data: TagTable) {
        val v = LayoutInflater.from(ctx).inflate(R.layout.table_tag, null, false)
        v.txtSubTableTag.text = data.sub
        v.RVTableTag.apply {
            layoutManager = GridLayoutManager(ctx, data.columns)
            adapter = AdapterTagTable(data.data)
        }
        LLHome.addView(v)
    }

    override fun productList(data: ProductList) {
        val v = LayoutInflater.from(ctx).inflate(R.layout.list_product, view as ViewGroup, false)
        v.txtSubListProduct.text = data.sub
        v.RVProduct.apply {
            layoutManager = LinearLayoutManager(ctx, RecyclerView.HORIZONTAL, false)
            adapter = AdapterProduct(data.data)
        }
        LLHome.addView(v)
    }

    override fun timingProductList(data: TimingProductList) {
        val v = LayoutInflater.from(ctx).inflate(R.layout.list_timing_product, null, false)
        v.txtSubListTimingProduct.text = data.sub
        startTimer(v.txtTimerListProduct)
        v.RVTimingProduct.apply {
            layoutManager = LinearLayoutManager(ctx, RecyclerView.HORIZONTAL, false)
            adapter = AdapterProduct(data.data)
        }
        LLHome.addView(v)
    }

    private fun startTimer(v:TextView) {
        val now = Calendar.getInstance()
        val tomorow = Calendar.getInstance()
        tomorow.add(Calendar.HOUR_OF_DAY, 5)
        val h = Handler()
        Thread(
            Runnable {
                while (tomorow.after(now))
                {
                    now.add(Calendar.SECOND, 1)
                    val t = Calendar.getInstance()
                    t.timeInMillis = tomorow.timeInMillis - now.timeInMillis
                    val time = t.get(Calendar.SECOND).toString()+" : "+t.get(Calendar.MINUTE).toString()+" : "+t.get(Calendar.HOUR_OF_DAY).toString()
                    h.post {
                        v.text = time
                    }
                    Thread.sleep(1000)
                }
            }
        ).start()
    }

    override fun tagListVerticaly(data: TagList) {
        val v = RecyclerView(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutManager = LinearLayoutManager(ctx)
            adapter = AdapterTagList(data.data, data.type)
        }
        LLHome.addView(v)
    }

    override fun tagListHorizontaly(data: TagList) {
        val v = RecyclerView(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutManager = LinearLayoutManager(ctx, RecyclerView.HORIZONTAL, false)
            adapter = AdapterTagList(data.data, data.type)
        }
        LLHome.addView(v)
    }

    override fun homeListPresenterResult(ok: Boolean, message: String) {
        progress.cancel()
        if (!ok)
            toast(message)
    }


}