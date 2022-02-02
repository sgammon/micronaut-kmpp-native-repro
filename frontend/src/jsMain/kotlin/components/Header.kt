package components

import util.Sizes
import csstype.*
import kotlinext.js.jso
import react.FC
import react.Props
import mui.icons.material.Menu
import mui.material.*
import react.dom.aria.ariaLabel
import react.dom.html.ReactHTML
import react.router.useLocation
import react.useContext


val Header = FC<Props> {
    val (sidebarOpened, setSidebarOpened) = useContext(SidebarOpenedContext)
    val lastPathname = useLocation().pathname.substringAfterLast("/")
    val ml = if (sidebarOpened) Sizes.Sidebar.Width else 0.px

    AppBar {
        position = AppBarPosition.fixed
        sx = jso {
            width = 100.pct - ml
            height = Sizes.Header.Height
            marginLeft = ml
        }

        Toolbar {
            IconButton {
                ariaLabel = "open drawer"
                edge = IconButtonEdge.start
                color = IconButtonColor.inherit
                onClick = { setSidebarOpened(true) }
                sx = jso {
                    marginRight = 16.px
                    if (sidebarOpened) display = Display.none
                }

                Menu()
            }

            Typography {
                sx = jso { flexGrow = FlexGrow(1.0) }
                variant = "h6"
                noWrap = true
                component = ReactHTML.div

                +"Demo App"
            }
        }
    }
}
