data class Vocabulary(
    var id : String,
    val date : String,
    val kanji : String,
    val hiragana : String,
    val type : String,
    val kanjiTranslate : String,
    val example : String,
    val exampleHiragana: String,
    val exampleTranslate : String,
    val examplehiraganaRuby : String,
    val kanjiRuby : String

){
    constructor():this("",
        "","","","","","","","","",
        "")
}