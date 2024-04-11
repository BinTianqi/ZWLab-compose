
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import zwlab.composeapp.generated.resources.Res
import zwlab.composeapp.generated.resources.check_fill0
import zwlab.composeapp.generated.resources.content_copy_fill0
import zwlab.composeapp.generated.resources.expand_more_fill0

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

@Composable
fun CheckBoxItem(text:String, checked:Boolean, onClick:(Boolean)->Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25))
            .clickable(onClick = {onClick(!checked)})
    ){
        Checkbox(
            checked = checked,
            onCheckedChange = onClick
        )
        Text(text = text)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExpandIcon(expanded:Boolean, onClick:()->Unit){
    val degrees: Float by animateFloatAsState(if (expanded) 180f else 0f)
    Icon(
        painter = painterResource(Res.drawable.expand_more_fill0), contentDescription = "expand textfield",
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
            .width(250.dp)
            .padding(10.dp)
    ){
        Text(
            text = desc,
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge,
            modifier = Modifier.padding(start = 10.dp, end = 15.dp)
        )
        FloatingActionButton(
            onClick = {
                copying = true
                coroutine.launch{ delay(1500); copying=false }
                writeClipBoard(char)
            },
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
            containerColor = colorScheme.primary, contentColor = colorScheme.onPrimary
        ){
            Icon(
                painter = painterResource(Res.drawable.content_copy_fill0),
                contentDescription = null,
                modifier = Modifier.alpha(1F - alpha)
            )
            Icon(
                painter = painterResource(Res.drawable.check_fill0),
                contentDescription = null,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}
