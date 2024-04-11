
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
