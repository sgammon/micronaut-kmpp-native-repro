import react.Props
import react.RBuilder
import react.RComponent
import react.State
import styled.css
import styled.styledDiv


external interface APIMessageProps : Props {
    var greeting: String
}

data class APIMessageState(val greeting: String) : State


class APIMessage(props: APIMessageProps) : RComponent<APIMessageProps, APIMessageState>(props) {
    init {
        state = APIMessageState(props.greeting)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +WelcomeStyles.textContainer
            }
            +state.greeting
        }
    }
}
