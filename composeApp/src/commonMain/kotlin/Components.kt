
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment

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
