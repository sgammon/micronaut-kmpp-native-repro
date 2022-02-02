
package hook

import util.Routes
import react.ReactNode
import react.useMemo



/** Factory for a page element. */
class PageFactory constructor (private val callable: () -> ReactNode?) {
    fun invoke(): ReactNode? = callable.invoke()

    companion object {
        /** Wrap the provided function as a page factory. */
        fun wrap(callable: () -> ReactNode?): PageFactory = PageFactory(callable)
    }
}

data class Page constructor (
    val key: String,
    val name: String,
    val factory: PageFactory
) {
    companion object {
        /** Create a page. */
        fun create(key: String, name: String, factory: () -> ReactNode?): Page = Page(
            key,
            name,
            PageFactory.wrap(factory)
        )
    }
}

data class FrameInfo(
    val appName: String,
    val pages: List<Pair<String, Page>>,
)

fun useFrame(): FrameInfo {
    return useMemo {
        FrameInfo(
            appName = "Demo",
            pages = Routes
        )
    }
}
