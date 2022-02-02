package components

import FrameContext
import csstype.FlexGrow
import csstype.px
import util.Sizes.Header
import kotlinext.js.jso
import mui.material.Typography
import mui.system.Box
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML
import react.router.Outlet
import react.router.Route
import react.router.Routes
import react.useContext


val Content = FC<Props> {
    val frame = useContext(FrameContext)

    Routes {
        Route {
            path = "/"
            element = Box.create {
                component = ReactHTML.main
                sx = jso {
                    flexGrow = FlexGrow(1.0)
                    marginTop = Header.Height
                    padding = 30.px
                }
                Outlet()
            }

            for ((key, spec) in frame.pages) {
                Route {
                    path = key
                    index = key == ""
                    element = spec.factory.invoke()
                }
            }

            Route {
                path = "*"
                element = Typography.create { +"404 Page Not Found" }
            }
        }
    }
}
