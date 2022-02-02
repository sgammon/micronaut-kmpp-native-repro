import api.greet
import components.Content
import components.Header
import components.Sidebar
import components.SidebarOpenedModule
import csstype.Display
import hook.FrameInfo
import hook.useFrame
import kotlinext.js.jso
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mui.system.Box
import react.FC
import react.Props
import react.PropsWithChildren
import react.createContext
import react.create
import react.dom.render
import react.router.dom.HashRouter
import kotlin.js.Promise


fun greetAPI(): Promise<String> {
    console.log("Executing `greet` via REST call...")
    return greet("REST").then {
        console.log("Received from API: '$it'")
        it
    }
}


@OptIn(DelicateCoroutinesApi::class)
fun main() {
    window.onload = {
        val appRoot = document.getElementById("app")
            ?: document.body!!
        greetAPI().then {
            console.log("REST API test complete.")
        }

        GlobalScope.launch {
           render(
                element = App.create(),
                container = appRoot
           )
        }
    }
}

val FrameContext = createContext<FrameInfo>()

val FrameModule = FC<PropsWithChildren> { props ->
    val users = useFrame()

    FrameContext.Provider(users) {
        props.children()
    }
}

private val App = FC<Props> {
    HashRouter {
        FrameModule {
            SidebarOpenedModule {
                Box {
                    sx = jso { display = Display.flex }

                    Header()
                    Sidebar()
                    Content()
                }
            }
        }
    }
}

