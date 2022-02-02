package util

import hook.Page
import pages.About
import pages.Home
import react.create



val Routes = listOf(
    "" to Page.create("", "Home", Home::create),
    "about" to Page.create("about", "About", About::create)
)
