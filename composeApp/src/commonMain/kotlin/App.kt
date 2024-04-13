import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.getTheme
import zwlab.composeapp.generated.resources.*

@Composable
fun App(dark: Boolean, changeTheme: (Boolean) -> Unit) {
    var page by remember{mutableStateOf("Home")}
    var showDialog by remember{mutableStateOf(false)}
    MaterialTheme(
        colorScheme = getTheme(dark)
    ){
        Scaffold(
            bottomBar = { NavBar(page){page=it} }
        ){paddingValues->
            if(page=="Insert"){ InsertZW(paddingValues) }
            if(page=="Remove"){ RemoveZW(paddingValues) }
            if(page=="Home"){ ZWList(paddingValues, dark, changeTheme){showDialog = true} }
            if(page=="Encode"){ EncodeZW(paddingValues) }
            if(page=="Decode"){ DecodeZW(paddingValues) }
            AboutDialog(showDialog){showDialog=false}
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DecodeZW(paddingValues: PaddingValues){
    var visible by remember{mutableStateOf("")}
    var hidden by remember{mutableStateOf("")}
    var input by remember{mutableStateOf("")}
    var expandVisible by remember{mutableStateOf(true)}
    var expandHidden by remember{mutableStateOf(true)}
    var expandInput by remember{mutableStateOf(true)}
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).verticalScroll(rememberScrollState())
    ){
        Spacer(Modifier.padding(vertical = 10.dp))
        TextField(
            value = input, onValueChange = {input = it},
            label = {Text(text = stringResource(Res.string.input))},
            trailingIcon = if(input.contains("\n")){ {ExpandIcon(expandInput) {expandInput = !expandInput}} }else{ null },
            modifier = Modifier.fillMaxWidth(),
            singleLine = !expandInput
        )
        Spacer(Modifier.padding(vertical = 2.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                decode(input).let{
                    visible = it.first
                    hidden = it.second
                }
                expandInput = false
            }
        ){
            Text(text = stringResource(Res.string.go))
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(visible!=""||hidden!=""){
            Column{
                TextField(
                    value = visible, onValueChange = {visible = it},
                    label = {Text(text = stringResource(Res.string.visible_text))},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = if(visible.contains("\n")){ {ExpandIcon(expandVisible) {expandVisible = !expandVisible}} }else{ null },
                    singleLine = !expandVisible
                )
                Spacer(Modifier.padding(vertical = 2.dp))
                TextField(
                    value = hidden, onValueChange = {hidden = it},
                    label = {Text(text = stringResource(Res.string.hidden_text))},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = if(hidden.contains("\n")){ {ExpandIcon(expandHidden) {expandHidden = !expandHidden}} }else{ null },
                    singleLine = !expandHidden
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ){
                    CopyButton(stringResource(Res.string.copy_visible), visible, 160.dp)
                    CopyButton(stringResource(Res.string.copy_hidden), hidden, 160.dp)
                }
            }
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EncodeZW(paddingValues: PaddingValues){
    var visible1 by remember{mutableStateOf("")}
    var hidden by remember{mutableStateOf("")}
    var visible2 by remember{mutableStateOf("")}
    var output by remember{mutableStateOf("")}
    var expandVisible1 by remember{mutableStateOf(true)}
    var expandHidden by remember{mutableStateOf(true)}
    var expandVisible2 by remember{mutableStateOf(true)}
    var expandOutput by remember{mutableStateOf(true)}
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).verticalScroll(rememberScrollState())
    ){
        Spacer(Modifier.padding(vertical = 10.dp))
        TextField(
            value = visible1, onValueChange = {visible1 = it},
            label = {Text(text = stringResource(Res.string.visible_text))},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if(visible1.contains("\n")){ {ExpandIcon(expandVisible1) {expandVisible1 = !expandVisible1}} }else{ null },
            singleLine = !expandVisible1
        )
        Spacer(Modifier.padding(vertical = 2.dp))
        TextField(
            value = hidden, onValueChange = {hidden = it},
            label = {Text(text = stringResource(Res.string.hidden_text))},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if(hidden.contains("\n")){ {ExpandIcon(expandHidden) {expandHidden = !expandHidden}} }else{ null },
            singleLine = !expandHidden
        )
        Spacer(Modifier.padding(vertical = 2.dp))
        TextField(
            value = visible2, onValueChange = {visible2 = it},
            label = {Text(text = stringResource(Res.string.visible_text))},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if(visible2.contains("\n")){ {ExpandIcon(expandVisible2) {expandVisible2 = !expandVisible2}} }else{ null },
            singleLine = !expandVisible2
        )
        Spacer(Modifier.padding(vertical = 3.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                output = encode(visible1, hidden, visible2)
                expandVisible1 = false
                expandHidden = false
                expandVisible2 = false
            }
        ){
            Text(text = stringResource(Res.string.go))
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(output!=""){
            Column{
                OutlinedTextField(
                    value = output, onValueChange = {output = it},
                    label = {Text(text = stringResource(Res.string.output))}, readOnly = true,
                    trailingIcon = if(output.contains("\n")){ {ExpandIcon(expandOutput) {expandOutput = !expandOutput}} }else{ null },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = !expandOutput
                )
                Spacer(Modifier.padding(vertical = 2.dp))
                CopyButton(stringResource(Res.string.copy), output)
            }
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ZWList(paddingValues: PaddingValues, dark:Boolean, changeTheme:(Boolean)->Unit, showDialog:()->Unit){
    val list = listOf(
        Pair("\u200B" ,stringResource(Res.string.zw_space)),
        Pair("\uFEFF" ,stringResource(Res.string.zw_no_break_space)),
        Pair("\u200C" ,stringResource(Res.string.zw_non_joiner)),
        Pair("\u200D" ,stringResource(Res.string.zw_joiner)),
        Pair("\u200E" ,stringResource(Res.string.ltr_mark)),
        Pair("\u200F" ,stringResource(Res.string.rtl_mark))
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = stringResource(Res.string.zero_width_lab), style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 3.dp)){
                Text(text = stringResource(Res.string.dark), modifier = Modifier.padding(end = 5.dp))
                Switch(onCheckedChange = changeTheme, checked = dark)
            }
        }
        if(getPlatform()=="android"){
            for(i in list){
                CopyZWCharacter(i.first, i.second)
            }
        }else{
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                CopyZWCharacter(list[0].first, list[0].second)
                CopyZWCharacter(list[1].first, list[1].second)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                CopyZWCharacter(list[2].first, list[2].second)
                CopyZWCharacter(list[3].first, list[3].second)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                CopyZWCharacter(list[4].first, list[4].second)
                CopyZWCharacter(list[5].first, list[5].second)
            }
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        if(getPlatform()!="desktop"){TextButton(onClick = showDialog){Text(stringResource(Res.string.about))}}
        Spacer(Modifier.padding(top = 30.dp, bottom = paddingValues.calculateBottomPadding()))
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun InsertZW(paddingValues: PaddingValues){
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
            label = {Text(text = stringResource(Res.string.input))},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if(input.contains("\n")){ {ExpandIcon(expandInput) {expandInput = !expandInput}} }else{ null },
            singleLine = !expandInput
        )
        Spacer(Modifier.padding(vertical = 3.dp))
        Text(
            text = stringResource(Res.string.zw_char_type_is),
            modifier = Modifier.align(Alignment.Start).padding(start = 6.dp, top = 6.dp, bottom = 2.dp).animateContentSize()
        )
        RadioButtonItem(text = stringResource(Res.string.zw_space), selected = selectedZW=="b", onClick = {selectedZW = "b"})
        RadioButtonItem(text = stringResource(Res.string.zw_no_break_space), selected = selectedZW=="feff", onClick = {selectedZW = "feff"})
        RadioButtonItem(text = stringResource(Res.string.zw_non_joiner), selected = selectedZW=="c", onClick = {selectedZW = "c"})
        RadioButtonItem(text = stringResource(Res.string.zw_joiner), selected = selectedZW=="d", onClick = {selectedZW = "d"})
        RadioButtonItem(text = stringResource(Res.string.ltr_mark), selected = selectedZW=="e", onClick = {selectedZW = "e"})
        RadioButtonItem(text = stringResource(Res.string.rtl_mark), selected = selectedZW=="f", onClick = {selectedZW = "f"})
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
            Text(text = stringResource(Res.string.go))
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(output!=""){
            Column{
                OutlinedTextField(
                    value = output, onValueChange = {output = it},
                    label = {Text(text = stringResource(Res.string.output))}, readOnly = true,
                    trailingIcon = if(output.contains("\n")){ {ExpandIcon(expandOutput) {expandOutput = !expandOutput}} }else{ null },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = !expandOutput
                )
                Spacer(Modifier.padding(vertical = 2.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = stringResource(Res.string.insert_stat,stats.toString()),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    CopyButton("Copy", output)
                }
            }
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RemoveZW(paddingValues: PaddingValues){
    var input by remember{mutableStateOf("")}
    var output by remember{mutableStateOf("")}
    var expandInput by remember{mutableStateOf(true)}
    var expandOutput by remember{mutableStateOf(true)}
    var stat by remember{mutableStateOf(0)}
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).verticalScroll(rememberScrollState())
    ){
        Spacer(Modifier.padding(vertical = 10.dp))
        TextField(
            value = input, onValueChange = {input = it},
            label = {Text(text = stringResource(Res.string.input))},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if(input.contains("\n")){ {ExpandIcon(expandInput) {expandInput = !expandInput}} }else{ null },
            singleLine = !expandInput
        )
        Spacer(Modifier.padding(vertical = 3.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                removeZW(input).let{
                    output = it.first
                    stat = it.second
                }
                expandInput = false
            }
        ){
            Text(text = stringResource(Res.string.go))
        }
        Spacer(Modifier.padding(vertical = 3.dp))
        AnimatedVisibility(output!=""){
            Column{
                OutlinedTextField(
                    value = output, onValueChange = {output = it},
                    label = {Text(text = stringResource(Res.string.output))}, readOnly = true,
                    trailingIcon = if(output.contains("\n")){ {ExpandIcon(expandOutput) {expandOutput = !expandOutput}} }else{ null },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = !expandOutput
                )
                Spacer(Modifier.padding(vertical = 2.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = stringResource(Res.string.remove_stat,stat.toString()),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    CopyButton(stringResource(Res.string.copy), output)
                }
            }
        }
        Spacer(Modifier.padding(top = paddingValues.calculateBottomPadding(), bottom = 60.dp).imePadding())
    }
}
