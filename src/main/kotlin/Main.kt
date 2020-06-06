import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.title


fun main() {

    val myFirebase = MyFirebase()
    val basicPath = "/jpvocabulary"
    val port = System.getenv("PORT")?.toInt() ?: 7070
    var date : String? = ""
    embeddedServer(Netty,port) {
        install(FreeMarker){
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
        routing {
            static("/static"){
                resources("static")
            }
            route("/"){
                get{


                    call.respond(FreeMarkerContent("index.ftl",
                        mapOf(
                            "jpvocabularys" to date?.let {
                                if (it.isBlank() || it.isEmpty()){
                                    myFirebase.readAll()
                                }else{
                                    myFirebase.readByDate(it)
                                }
                            },
                            "data" to myFirebase.getAllDate(),
                            "beforevalue" to date
                        )
                    ))


                }
            }
            route(basicPath){
                get {
                    val action = (call.request.queryParameters["action"] ?: "new")
                    when(action){
                        "new" -> call.respond(
                            FreeMarkerContent("jpvocabulary.ftl",
                                mapOf(
                                    "action" to action,
                                    "DATE" to myFirebase.nowDate(),
                                    "TYPES" to arrayListOf(
                                        "自動 I","自動 II",
                                        "他動 I","他動 II",
                                        "する名詞","名詞",
                                        "い形容詞","な形容詞",
                                        "副詞","文法"
                                    )

                                ))
                        )
                        "edit" -> {
                            val id = call.request.queryParameters["id"]
                            if(id != null){
                                call.respond(
                                        FreeMarkerContent("jpvocabulary.ftl",
                                            mapOf("jpvocabulary" to myFirebase.readById(id),
                                                "action" to action))
                                        )
                            }
                        }
                    }
                }
                post{
                    val postParameters: Parameters = call.receiveParameters()
                    val action = postParameters["action"] ?: "new"
                    when(action){
                        "new" -> myFirebase.insert(
                            Vocabulary(
                                postParameters["id"]?:"",
                                postParameters["date"]?:"",
                                postParameters["kanji"]?:"",
                                postParameters["hiragana"]?:"",
                                postParameters["type"]?:"",
                                postParameters["kanjiTranslate"]?:"",
                                postParameters["example"]?:"",
                                postParameters["exampleHiragana"]?:"",
                                postParameters["exampleTranslate"]?:"",
                                postParameters["examplehiraganaRuby"]?:"",
                                postParameters["kanjiRuby"]?:""

                            )
                          )
                        "edit" ->{
                            val id = postParameters["id"]
                            if(id != null)
                                myFirebase.update(
                                    Vocabulary(
                                        postParameters["id"]?:"",
                                        postParameters["date"]?:"",
                                        postParameters["kanji"]?:"",
                                        postParameters["hiragana"]?:"",
                                        postParameters["type"]?:"",
                                        postParameters["kanjiTranslate"]?:"",
                                        postParameters["example"]?:"",
                                        postParameters["exampleHiragana"]?:"",
                                        postParameters["exampleTranslate"]?:"",
                                        postParameters["examplehiraganaRuby"]?:"",
                                        postParameters["kanjiRuby"]?:""
                                    )
                                )

                        }
                    }
                    call.respondRedirect("/", permanent = false)
                    call.respond(FreeMarkerContent("index.ftl", mapOf("jpvocabularys" to myFirebase.readAll())))
                }
            }
            route("/delete"){
                get{
                    val id = call.request.queryParameters["id"]
                    if(id != null){
                        myFirebase.delete(id.toString())
                        call.respondRedirect("/", permanent = false)
                        call.respond(FreeMarkerContent("index.ftl", mapOf("jpvocabularys" to myFirebase.readAll())))
                    }
                }
            }


            route("/select"){
                get{
                    println("select")
                    date = call.request.queryParameters["date"]
                    if(date != null){
                        call.respondRedirect("/", permanent = false)
                        call.respond(FreeMarkerContent("index.ftl", mapOf("jpvocabularys" to myFirebase.readByDate(date!!))))
                    }
                }
            }
        }

    }.start(wait = true)

}

