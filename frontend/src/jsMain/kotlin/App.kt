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
        GlobalScope.launch {
            greetAPI().then {
                console.log("Finished API call test.")

//                render(document.getElementById("root")!!) {
//                    child(Welcome::class) {
//                        attrs {
//                            name = "Kotlin/JS"
//                        }
//                    }
//                    child(APIMessage::class) {
//                        attrs {
//                            greeting = renderedGreeting
//                        }
//                    }
//                }
            }

            render(
                element = App.create(),
                container = document.createElement("div").also {
                    it.id = "root"
                    it.classList.add("app-root")
                    document.body!!.appendChild(it)
                },
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
