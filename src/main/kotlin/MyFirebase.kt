import com.google.api.core.ApiFuture
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QuerySnapshot
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class MyFirebase : Actions{
    private var fireStore: Firestore
    private val collectionPath = "jpvocabulary"
    private val jsonFilePath = "firebasekey.json"
    private val databaseUrl = "https://remembertango.firebaseio.com"

    init {
        val serviceAccount = FileInputStream(jsonFilePath)
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(databaseUrl)
            .build()

        FirebaseApp.initializeApp(options)
        fireStore = FirestoreClient.getFirestore()
    }

    override fun readAll(): ArrayList<Vocabulary> {
        val resultList = arrayListOf<Vocabulary>()
        val apiFutures : ApiFuture<QuerySnapshot> = fireStore.collection(collectionPath).get()
        apiFutures.get().documents.forEach {
            resultList.add(it.toObject(Vocabulary::class.java))
        }
        return resultList
    }

    override fun getAllDate(): ArrayList<String> {
        val resultList = arrayListOf<String>()
        val tempSet = HashSet<String>()
        for (vocabulary in readAll()) {
            tempSet.add(vocabulary.date)
        }
        tempSet.forEach {
            resultList.add(it)
        }
        resultList.sorted()
        return resultList
    }

    override fun readByDate(date: String): ArrayList<Vocabulary> {
        if (date=="全部")return readAll()
        val resultList = arrayListOf<Vocabulary>()
        for (vocabulary in readAll()) {
            if (vocabulary.date == date){
                resultList.add(vocabulary)
            }
        }
        return resultList
    }

    override fun readById(id: String): Vocabulary {
        val apiFutures : DocumentReference = fireStore.collection(collectionPath).document(id)
        return apiFutures.get().get().toObject(Vocabulary::class.java)!!
    }
    override fun insert(vocabulary: Vocabulary): String {
        val apiFutures : DocumentReference = fireStore.collection(collectionPath).document()
        vocabulary.id = apiFutures.id
        val result = apiFutures.set(vocabulary)
        return dateToString(result.get().updateTime.toDate())

    }

    override fun update(vocabulary: Vocabulary): String {
        val apiFuture = fireStore.collection(collectionPath).document(vocabulary.id)
        return if (apiFuture.get().get().exists()){
            val result =  apiFuture.set(vocabulary)
            dateToString(result.get().updateTime.toDate())
        }else{
            "Update >> No Data "
        }

    }

    override fun delete(id: String) : String {
        val apiFuture = fireStore.collection(collectionPath).document(id)
        return if (!apiFuture.get().get().exists()){
            "Delete >> No data"
        }else{
            val result = apiFuture.delete()
            dateToString(result.get().updateTime.toDate())
        }

    }


    private fun dateToString(date: Date) : String{
        val pattern = "yyyy/MM/dd"
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    fun nowDate() : String{
        val pattern = "yyyy/MM/dd"
        val format = SimpleDateFormat(pattern)
        return format.format(Date())
    }

}