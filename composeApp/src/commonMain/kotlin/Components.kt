
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.*
import zwlab.composeapp.generated.resources.*

@Composable
fun RadioButtonItem(text:String, selected:Boolean, onClick:()->Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25))
            .clickable(onClick = onClick)
    ){
        RadioButton(
            selected = selected,
            onClick = onClick,
        )
        Text(text = text)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExpandIcon(expanded:Boolean, onClick:()->Unit){
    val degrees: Float by animateFloatAsState(if (expanded) 180f else 0f)
    Icon(
        painter = painterResource(Res.drawable.expand_more_fill0),
        contentDescription = stringResource(if(expanded){Res.string.shrink_textfield}else{Res.string.expand_textfield}),
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .rotate(degrees)
            .padding(3.dp)
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CopyZWCharacter(char:String, desc:String){
    var copying by remember{mutableStateOf(false)}
    val alpha: Float by animateFloatAsState(targetValue = if (copying) 1f else 0f, animationSpec = animateAlpha)
    val coroutine = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(25))
            .background(colorScheme.primaryContainer)
            .width(270.dp)
            .padding(10.dp)
    ){
        Text(
            text = desc,
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        IconButton(
            onClick = {
                copying = true
                coroutine.launch{ delay(1500); copying=false }
                writeClipBoard(char)
            },
            colors = IconButtonDefaults.iconButtonColors(contentColor = colorScheme.primary)
        ){
            Icon(
                painter = painterResource(Res.drawable.content_copy_fill0),
                contentDescription = stringResource(Res.string.copy),
                modifier = Modifier.alpha(1F - alpha)
            )
            Icon(
                painter = painterResource(Res.drawable.check_fill0),
                contentDescription = stringResource(Res.string.copied),
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CopyButton(text:String, content:String, minWidth:Dp=Dp.Unspecified){
    var copying by remember{mutableStateOf(false)}
    val coroutine = rememberCoroutineScope()
    val alpha: Float by animateFloatAsState(targetValue = if (copying) 1f else 0f, animationSpec = animateAlpha)
    Button(
        onClick = {
            copying = true
            coroutine.launch{delay(1500); copying = false}
            writeClipBoard(content)
        },
        modifier = Modifier.widthIn(min = minWidth)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.animateContentSize()
        ){
            Box{
                Icon(
                    painter = painterResource(Res.drawable.content_copy_fill0),
                    contentDescription = stringResource(Res.string.copy),
                    modifier = Modifier.alpha(1F - alpha)
                )
                Icon(
                    painter = painterResource(Res.drawable.check_fill0),
                    contentDescription = stringResource(Res.string.copied),
                    modifier = Modifier.alpha(alpha)
                )
            }
            Text(
                text = if(copying){stringResource(Res.string.copied)}else{text},
                modifier = Modifier.animateContentSize().padding(start = 5.dp)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavBar(page:String, setPage:(String)->Unit){
    NavigationBar{
        for(item in pageList){
            NavigationBarItem(
                selected = page==item.id, label = {Text(text = stringResource(item.name))},
                onClick = {setPage(item.id)},
                icon = {Icon(painterResource(item.icon),contentDescription = null)}
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AboutDialog(showDialog:Boolean, onDismiss:()->Unit){
    if(showDialog){
        var copying by remember{mutableStateOf(false)}
        val coroutine = rememberCoroutineScope()
        val url = stringResource(Res.string.project_on_github_url)
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {Icon(painterResource(Res.drawable.info_fill0), contentDescription = "App icon")},
            title = {Text(text = stringResource(Res.string.zero_width_lab))},
            text = {
                Column{
                    Text(text = stringResource(Res.string.author_info))
                    Text(text = stringResource(Res.string.project_on_github))
                    SelectionContainer{
                        Text(text = url, color = colorScheme.onPrimaryContainer)
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        copying = true
                        coroutine.launch{ delay(1500); copying = false}
                        writeClipBoard(url)
                    },
                    modifier = Modifier.width(100.dp)
                ){
                    Text(
                        text = stringResource(if(copying){Res.string.copied}else{Res.string.copy_link}),
                        modifier = Modifier.animateContentSize()
                    )
                }
            },
            confirmButton = {TextButton(onClick = onDismiss){Text(text = stringResource(Res.string.confirm))} },
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }
}

data class PageListItem @OptIn(ExperimentalResourceApi::class) constructor(
    val id: String,
    val name: StringResource,
    val icon: DrawableResource
)

@OptIn(ExperimentalResourceApi::class)
val pageList = listOf(
    PageListItem("Insert",Res.string.insert,Res.drawable.add_fill0),
    PageListItem("Remove",Res.string.remove,Res.drawable.remove_fill0),
    PageListItem("Home",Res.string.home,Res.drawable.format_list_bulleted_fill0),
    PageListItem("Encode",Res.string.encode,Res.drawable.lock_fill0),
    PageListItem("Decode",Res.string.decode,Res.drawable.lock_open_right_fill0)
)
