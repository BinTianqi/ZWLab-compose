import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zwlab.composeapp.generated.resources.Res
import zwlab.composeapp.generated.resources.compose_multiplatform
import zwlab.composeapp.generated.resources.key_fill0

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar{
                    NavigationBarItem(
                        selected = true, label = {Text(text = "ZWJ")},
                        onClick = {}, icon = {Icon(painterResource(Res.drawable.key_fill0),contentDescription = null)}
                    )
                }
            }
        ){paddingValues->
            EscapeRegex(paddingValues)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EscapeRegex(paddingValues: PaddingValues){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).verticalScroll(rememberScrollState())
    ){
        var input by remember{mutableStateOf("")}
        var output by remember{mutableStateOf("")}
        var selectedZW by remember{mutableStateOf("b")}
        var removeMode by remember{mutableStateOf(false)}
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        TextField(
            value = input, onValueChange = {input = it},
            label = {Text(text = "Input")},
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Start)
                .clip(RoundedCornerShape(25))
                .clickable(onClick = {removeMode = !removeMode})
        ){
            Checkbox(checked = removeMode, onCheckedChange = {removeMode = it})
            Spacer(Modifier.padding(horizontal = 2.dp))
            Text(text = "Remove mode")
            Spacer(Modifier.padding(horizontal = 3.dp))
        }
        AnimatedVisibility(!removeMode){
            Column{
                RadioButtonItem(text = "Zero-width space", selected = selectedZW=="b", onClick = {selectedZW = "b"})
                RadioButtonItem(text = "Zero-width non-joiner", selected = selectedZW=="c", onClick = {selectedZW = "c"})
                RadioButtonItem(text = "Zero-width joiner", selected = selectedZW=="d", onClick = {selectedZW = "d"})
                RadioButtonItem(text = "Left-to-right mark", selected = selectedZW=="e", onClick = {selectedZW = "e"})
            }
        }
        Button(
            onClick = {
                output = if(removeMode){ removeZW(input) }
                else{ addZW(input,selectedZW) }
            }
        ){
            Text(text = " Go! ")
        }
        AnimatedVisibility(output!=""){
            TextField(
                value = output, onValueChange = {output = it},
                label = {Text(text = "Output")},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp))
    }
}
