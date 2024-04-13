import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.getTheme
import zwlab.composeapp.generated.resources.Res
import zwlab.composeapp.generated.resources.about
import zwlab.composeapp.generated.resources.info_fill0
import zwlab.composeapp.generated.resources.zw_lab

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    var dark:Boolean by remember{mutableStateOf(false)}
    dark = isSystemInDarkTheme()
    Window(
        onCloseRequest = ::exitApplication, title = stringResource(Res.string.zw_lab)
    ) {
        DesktopApp(dark){dark=it}
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DesktopApp(dark:Boolean, changeTheme:(Boolean)->Unit){
    var page by remember{mutableStateOf("Home")}
    var showAboutDialog by remember{mutableStateOf(false)}
    val list = listOf(pageList[2] ,pageList[0], pageList[1], pageList[3], pageList[4])
    MaterialTheme(colorScheme = getTheme(dark)){
        Row(
            modifier = Modifier.fillMaxSize()
        ){
            NavigationRail(
                containerColor = colorScheme.surfaceContainer
            ){
                Spacer(Modifier.padding(vertical = 5.dp))
                Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()){
                    Column{
                        for(item in list){
                            NavigationRailItem(
                                icon = {Icon(painter = painterResource(item.icon), contentDescription = stringResource(item.name))},
                                label = {Text(text = stringResource(item.name))},
                                selected = page==item.id,
                                onClick = {page=item.id}
                            )
                        }
                    }
                    NavigationRailItem(
                        icon = {Icon(painter = painterResource(Res.drawable.info_fill0), contentDescription = stringResource(Res.string.about))},
                        label = {Text(text = stringResource(Res.string.about))},
                        selected = false,
                        onClick = {showAboutDialog =  true}
                    )
                }
            }
            Scaffold{
                if(page=="Home"){ ZWList(PaddingValues(), dark, changeTheme){showAboutDialog = true} }
                if(page=="Insert"){ InsertZW(PaddingValues()) }
                if(page=="Remove"){ RemoveZW(PaddingValues()) }
                if(page=="Encode"){ EncodeZW(PaddingValues()) }
                if(page=="Decode"){ DecodeZW(PaddingValues()) }
            }
        }
        AboutDialog(showAboutDialog){showAboutDialog = false}
    }
}
