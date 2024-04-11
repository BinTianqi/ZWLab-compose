import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zwlab.composeapp.generated.resources.*

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    var page by remember{mutableStateOf("ZWList")}
    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar{
                    NavigationBarItem(
                        selected = page == "Insert", label = {Text(text = "Insert")},
                        onClick = {page = "Insert"},
                        icon = {Icon(painterResource(Res.drawable.add_fill0),contentDescription = null)}
                    )
                    NavigationBarItem(
                        selected = page == "ZWList", label = {Text(text = "ZW list")},
                        onClick = {page = "ZWList"},
                        icon = {Icon(painterResource(Res.drawable.glyphs_fill0),contentDescription = null)}
                    )
                    NavigationBarItem(
                        selected = page == "Remove", label = {Text(text = "Remove")},
                        onClick = {page = "Remove"},
                        icon = {Icon(painterResource(Res.drawable.remove_fill0),contentDescription = null)}
                    )
                }
            }
        ){paddingValues->
            if(page=="Insert"){
                InsertZW(paddingValues)
            }
            if(page=="Remove"){
                val removeZW = mutableStateMapOf("b" to true, "c" to true, "d" to true, "e" to true, "f" to true, "feff" to true)
                RemoveZW(paddingValues, removeZW)
            }
            if(page=="ZWList"){
                ZWList(paddingValues)
            }
        }
    }
}

@Composable
fun ZWList(paddingValues: PaddingValues){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ){
        Spacer(Modifier.padding(vertical = 10.dp))
        CopyZWCharacter("\u200B" ,"ZW space")
        CopyZWCharacter("\uFEFF" ,"ZW no-break space")
        CopyZWCharacter("\u200C" ,"ZW non-joiner")
        CopyZWCharacter("\u200D" ,"ZW joiner")
        CopyZWCharacter("\u200E" ,"LTR mark")
        CopyZWCharacter("\u200F" ,"RTL mark")
        Spacer(Modifier.padding(top = 60.dp, bottom = paddingValues.calculateBottomPadding()))
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun InsertZW(paddingValues: PaddingValues){
    var input by remember{mutableStateOf("")}
    var output by remember{mutableStateOf("")}
    var selectedZW by remember{mutableStateOf("b")}
    var expandInput by remember{mutableStateOf(true)}
    var expandOutput by remember{mutableStateOf(true)}
    var stats by remember{mutableIntStateOf(0)}
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
        Text(
            text = "Add ZW character type:",
            modifier = Modifier.align(Alignment.Start).padding(start = 6.dp, top = 6.dp, bottom = 2.dp).animateContentSize()
        )
        RadioButtonItem(text = "ZW space", selected = selectedZW=="b", onClick = {selectedZW = "b"})
        RadioButtonItem(text = "ZW no-break space", selected = selectedZW=="feff", onClick = {selectedZW = "feff"})
        RadioButtonItem(text = "ZW non-joiner", selected = selectedZW=="c", onClick = {selectedZW = "c"})
        RadioButtonItem(text = "ZW joiner", selected = selectedZW=="d", onClick = {selectedZW = "d"})
        RadioButtonItem(text = "LTR mark", selected = selectedZW=="e", onClick = {selectedZW = "e"})
        RadioButtonItem(text = "RTL mark", selected = selectedZW=="f", onClick = {selectedZW = "f"})
        Button(
            onClick = {
                focusManager.clearFocus()
                addZW(input,selectedZW).let{
                    output = it.first
                    stats = it.second
                }
                expandInput = false
            }
        ){
            Text(text = " Go! ")
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(output!=""){
            Column{
                OutlinedTextField(
                    value = output, onValueChange = {output = it},
                    label = {Text(text = "Output")}, readOnly = true,
                    trailingIcon = if(output.contains("\n")){ {ExpandIcon(expandOutput) {expandOutput = !expandOutput}} }else{ null },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = !expandOutput
                )
                Spacer(Modifier.padding(vertical = 2.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    var copying by remember{mutableStateOf(false)}
                    val coroutine = rememberCoroutineScope()
                    Text(
                        text = "Added $stats ZW chars.",
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Button(
                        onClick = {
                            copying = true
                            coroutine.launch{delay(1500); copying = false}
                            writeClipBoard(output)
                        },
                        modifier = Modifier.animateContentSize()
                    ){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Icon(painter = painterResource(Res.drawable.content_copy_fill0), contentDescription = null)
                            Text(text = if(copying){"  Copied"}else{"  Copy"})
                        }
                    }
                }
            }
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}



@OptIn(ExperimentalResourceApi::class)
@Composable
fun RemoveZW(paddingValues: PaddingValues, removeZW:SnapshotStateMap<String,Boolean>){
    var input by remember{mutableStateOf("")}
    var output by remember{mutableStateOf("")}
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
        Text(
            text = "Remove ZW character type:",
            modifier = Modifier.align(Alignment.Start).padding(start = 6.dp, top = 6.dp, bottom = 2.dp).animateContentSize()
        )
        CheckBoxItem(text = "ZW space", checked = removeZW["b"]==true, onClick = {removeZW["b"]=it})
        CheckBoxItem(text = "ZW no-break space", checked = removeZW["feff"]==true, onClick = {removeZW["feff"]=it})
        CheckBoxItem(text = "ZW non-joiner", checked = removeZW["c"]==true, onClick = {removeZW["c"]=it})
        CheckBoxItem(text = "ZW joiner", checked = removeZW["d"]==true, onClick = {removeZW["d"]=it})
        CheckBoxItem(text = "LTR mark", checked = removeZW["e"]==true, onClick = {removeZW["e"]=it})
        CheckBoxItem(text = "RTL mark", checked = removeZW["f"]==true, onClick = {removeZW["f"]=it})
        Button(
            onClick = {
                focusManager.clearFocus()
                val charSet = mutableSetOf<String>()
                if(removeZW["b"]==true){charSet.add("\u200B")}
                if(removeZW["c"]==true){charSet.add("\u200C")}
                if(removeZW["d"]==true){charSet.add("\u200D")}
                if(removeZW["e"]==true){charSet.add("\u200E")}
                if(removeZW["f"]==true){charSet.add("\u200F")}
                if(removeZW["feff"]==true){charSet.add("\uFEFF")}
                output = removeZW(input, charSet)
                expandInput = false
            }
        ){
            Text(text = " Go! ")
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(output!=""){
            var copying by remember{mutableStateOf(false)}
            val coroutine = rememberCoroutineScope()
            Column{
                OutlinedTextField(
                    value = output, onValueChange = {output = it},
                    label = {Text(text = "Output")}, readOnly = true,
                    trailingIcon = if(output.contains("\n")){ {ExpandIcon(expandOutput) {expandOutput = !expandOutput}} }else{ null },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = !expandOutput
                )
                Spacer(Modifier.padding(vertical = 2.dp))
                Button(
                    onClick = {
                        copying = true
                        coroutine.launch{delay(1500); copying = false}
                        writeClipBoard(output)
                    },
                    modifier = Modifier.animateContentSize().align(Alignment.End)
                ){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Icon(painter = painterResource(Res.drawable.content_copy_fill0), contentDescription = null)
                        Text(text = if(copying){"  Copied"}else{"  Copy"})
                    }
                }
            }
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}
