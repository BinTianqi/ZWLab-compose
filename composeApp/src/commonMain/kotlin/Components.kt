
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import zwlab.composeapp.generated.resources.Res
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
