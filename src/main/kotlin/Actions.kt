interface Actions {

    fun readAll() : ArrayList<Vocabulary>
    fun readByDate(date: String) : ArrayList<Vocabulary>
    fun readById(id: String) : Vocabulary
    fun insert(vocabulary: Vocabulary) : String
    fun update(vocabulary: Vocabulary) : String
    fun delete(id: String) : String
    fun getAllDate():ArrayList<String>
}