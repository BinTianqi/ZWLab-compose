import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zwlab.composeapp.generated.resources.Res
import zwlab.composeapp.generated.resources.text_fields_fill0

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar{
                    NavigationBarItem(
                        selected = true, label = {Text(text = "Character")},
                        onClick = {}, icon = {Icon(painterResource(Res.drawable.text_fields_fill0),contentDescription = null)}
                    )
                }
            }
        ){paddingValues->
            EscapeRegex(paddingValues)
        }
    }
}

@Composable
private fun EscapeRegex(paddingValues: PaddingValues){
    var input by remember{mutableStateOf("")}
    var output by remember{mutableStateOf("")}
    var selectedZW by remember{mutableStateOf("b")}
    var removeMode by remember{mutableStateOf(false)}
    val removeZW = mutableStateMapOf("b" to true, "c" to true, "d" to true, "e" to true)
    var expandInput by remember{mutableStateOf(true)}
    var expandOutput by remember{mutableStateOf(true)}
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).verticalScroll(rememberScrollState())
    ){
        Spacer(Modifier.padding(vertical = 10.dp))
        TextField(
            value = input, onValueChange = {input = it},
            label = {Text(text = "Input")},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if(input.contains("\n")){ {ExpandIcon(expandInput) {expandInput = !expandInput}} }else{ null },
            singleLine = !expandInput
        )
        Spacer(Modifier.padding(vertical = 3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Start)
                .clip(RoundedCornerShape(25))
                .clickable(onClick = {removeMode = !removeMode})
        ){
            Checkbox(checked = removeMode, onCheckedChange = {removeMode = it})
            Text(text = "Remove mode", modifier = Modifier.padding(start = 5.dp, end = 10.dp))
        }
        Text(
            text = if(removeMode){"Remove ZW character type:"}else{"Add ZW character type:"},
            modifier = Modifier.align(Alignment.Start).padding(start = 6.dp, top = 6.dp, bottom = 2.dp).animateContentSize()
        )
        AnimatedVisibility(!removeMode){
            Column{
                RadioButtonItem(text = "ZW space", selected = selectedZW=="b", onClick = {selectedZW = "b"})
                RadioButtonItem(text = "ZW non-joiner", selected = selectedZW=="c", onClick = {selectedZW = "c"})
                RadioButtonItem(text = "ZW joiner", selected = selectedZW=="d", onClick = {selectedZW = "d"})
                RadioButtonItem(text = "LTR mark", selected = selectedZW=="e", onClick = {selectedZW = "e"})
            }
        }
        AnimatedVisibility(removeMode){
            Column{
                CheckBoxItem(text = "ZW space", checked = removeZW["b"]==true, onClick = {removeZW["b"]=it})
                CheckBoxItem(text = "ZW non-joiner", checked = removeZW["c"]==true, onClick = {removeZW["c"]=it})
                CheckBoxItem(text = "ZW joiner", checked = removeZW["d"]==true, onClick = {removeZW["d"]=it})
                CheckBoxItem(text = "LTR mark", checked = removeZW["e"]==true, onClick = {removeZW["e"]=it})
            }
        }
        Button(
            onClick = {
                focusManager.clearFocus()
                output = if(removeMode){
                    val charSet = mutableSetOf<String>()
                    if(removeZW["b"]==true){charSet.add("\u200B")}
                    if(removeZW["c"]==true){charSet.add("\u200C")}
                    if(removeZW["d"]==true){charSet.add("\u200D")}
                    if(removeZW["e"]==true){charSet.add("\u200E")}
                    removeZW(input, charSet)
                }else{
                    addZW(input,selectedZW)
                }
                expandInput = false
            }
        ){
            Text(text = " Go! ")
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(output!=""){
            OutlinedTextField(
                value = output, onValueChange = {output = it},
                label = {Text(text = "Output")}, readOnly = true,
                trailingIcon = if(output.contains("\n")){ {ExpandIcon(expandOutput) {expandOutput = !expandOutput}} }else{ null },
                modifier = Modifier.fillMaxWidth(),
                singleLine = !expandOutput
            )
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}
