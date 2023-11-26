import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetfish.R

class MessageScrollView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_page_chatinterface)

        // 获取 ScrollView 对象
        val scrollView: ScrollView = findViewById(R.id.scrollView)

        // 创建一个 LinearLayout 作为 ScrollView 的子视图容器
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL

        // 循环加载多个列表项
        val numberOfItems = 5 // 你想要加载的项的数量
        for (i in 0 until numberOfItems) {
            // 使用 LayoutInflater 从 item_layout.xml 中创建视图对象
            val inflater = LayoutInflater.from(this)
            val itemView1 = inflater.inflate(R.layout.activity_message_page_chatinterface_buyer_item, null)
            val itemView2 = inflater.inflate(R.layout.activity_message_page_chatinterface_seller_item, null)
            // 添加该项到 LinearLayout 中
            linearLayout.addView(itemView1)
            linearLayout.addView(itemView2)
        }

        // 将 LinearLayout 添加到 ScrollView 中
        scrollView.addView(linearLayout)
    }
}
