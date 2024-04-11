fun addZW(input:String, type:String):Pair<String,Int>{
    val length = input.length
    var count = 0
    var index = 0
    val builder = StringBuilder(input)
    val zw = when(type){"b"->"\u200B"; "c"->"\u200C"; "d"->"\u200D"; "e"->"\u200E"; "f"->"\u200F"; else->" "}
    while(count<length){
        builder.insert(index,zw)
        count+=1
        index+=2
    }
    return Pair(builder.toString(),count)
}

fun removeZW(input: String, charSet:Set<String>):String{
    var output = input
    for(zw in charSet){ output = output.replace(zw,"") }
    return output
}

fun encode(visible1:String, hidden:String, visible2:String, method:String="bin"):String{
    var output = ""
    when(method){
        "bin"->{
            output+=visible1
            var bin = ""
            hidden.forEach{ bin += it.code.toString(2); bin+="\u200D" }
            bin.forEach{ output += if(it=='0'){"\u200C"}else if(it=='1'){"\u200B"}else{"\u200D"} }
            output+=visible2
        }
    }
    return output
}

fun decode(content:String, method:String="bin"):Pair<String,String>{
    var visible = ""
    var hidden = ""
    when(method){
        "bin"->{
            var bin = ""
            content.forEach{
                if(it.toString() in zwDict){
                    if(it.toString()=="\u200C"){bin += "0"}
                    if(it.toString()=="\u200B"){bin += "1"}
                    if(it.toString()=="\u200D"){ hidden+=bin.toInt(2).toChar(); bin="" }
                }else{
                    visible += it
                }
            }
        }
    }
    return Pair(visible,hidden)
}

val zwDict = setOf("\u200B", "\u200C", "\u200D", "\u200E", "\u200F", "\uFEFF")
