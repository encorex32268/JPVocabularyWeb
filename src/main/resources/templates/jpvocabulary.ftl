<#import "template.ftl" as layout />
<@layout.mainLayout title="New Vocabulary">
 <script src="/static/node_modules/kuroshiro/dist/kuroshiro.min.js"></script>
        <script src="/static/node_modules/kuroshiro-analyzer-kuromoji/dist/kuroshiro-analyzer-kuromoji.min.js"></script>

    <form action="/jpvocabulary" method="post">
        <div class="form-group">
            <label for="name">Date</label>
            <input type="text" class="form-control" id="date" name="date"
             value="${(jpvocabulary.date)!}">
        </div>
        <div class="form-group">

        <table>
            <th>
                <td>
                 <label for="kanji">字</label>
                                    <input type="text" class="form-control" id="kanji" name="kanji"
                                     value="${(jpvocabulary.kanji)!}">
                </td>
                <td>&nbsp&nbsp&nbsp&nbsp</td>
                 <td>
                  <label for="hiragana">假名</label>
                       <input type="text" class="form-control" id="hiragana" name="hiragana"
                            value="${(jpvocabulary.hiragana)!}">
                </td>
            </th>
        </table>

        </div>

                <div class="form-group">
                            <label for="type">詞性</label>
                             <select id="selectType" name="selectType" onchange="setTypeData(this)" >
                                        <option value="">請選擇</option>
                                        <#list TYPES as types>
                                         <option value="${types}">${types}</option>
                                         </#list>


                            </select>
                        </div>
                        <div class="form-group">
                                    <label for="kanjiTranslate">解釋</label>
                                            <input type="text" class="form-control" id="kanjiTranslate" name="kanjiTranslate"
                                             value="${(jpvocabulary.kanjiTranslate)!}">
                                </div>
                                <div class="form-group">
                                            <label for="example">例句</label>
                                                    <input type="text" class="form-control" id="example" name="example"
                                                     value="${(jpvocabulary.example)!}">
                                        </div>

                                        <div class="form-group">
                                                    <label for="exampleHiragana">例句假名</label>
                                                            <input type="text" class="form-control" id="exampleHiragana" name="exampleHiragana"
                                                             value="${(jpvocabulary.exampleHiragana)!}">
                                                </div>

                                                  <div class="form-group">
                                                      <label for="exampleTranslate">例句解釋</label>
                                                                 <input type="text" class="form-control" id="exampleTranslate" name="exampleTranslate"
                                                                           value="${(jpvocabulary.exampleTranslate)!}">
                                                                                                </div>

        <input type="hidden" id="examplehiraganaRuby" name="examplehiraganaRuby" value="${(jpvocabulary.examplehiraganaRuby)!}">
        <input type="hidden" id="kanjiRuby" name="kanjiRuby" value="${(jpvocabulary.kanjiRuby)!}">
        <input type="hidden" id="type" name="type" value="${(jpvocabulary.type)!}">
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="id" name="id" value="${(jpvocabulary.id)!}">
        <button type="button" class="btn btn-primary" onclick="confirm()">Confirm</button>
        <button id="submit" type="submit" class="btn btn-primary">Submit</button>

        <script type="text/javascript">
        var today = new Date();
        var date = today.getFullYear()+'/'+(today.getMonth()+1)+'/'+today.getDate();
        var dateTime = date;
        document.getElementById("date").value = dateTime;

      </script>

        <script>

        document.getElementById("selectType").value = document.getElementById("type").value;

                    function setTypeData(selectObject){
                        document.getElementById("type").value = selectObject.value;
                   }
                   function confirm(){
                     var covertStringExample = document.getElementById("example").value;
                     var covertStringKanji = document.getElementById("kanji").value;
                      var kuroshiro = new Kuroshiro();
                      kuroshiro.init(new KuromojiAnalyzer({ dictPath: "static/node_modules/dict/" }))
                           .then(function () {
                          return kuroshiro.convert(covertStringExample, {to:"hiragana"});
                        })
                        .then(function(result){
                       document.getElementById("exampleHiragana").value = result;
                        })
                         .then(function () {
                           return kuroshiro.convert(covertStringExample, {mode:"furigana",to:"hiragana"});
                            })
                           .then(function(result){
                             document.getElementById("examplehiraganaRuby").value = result;
                              })
                                .then(function () {
                                  return kuroshiro.convert(covertStringKanji, {to:"hiragana"});
                                  })
                                 .then(function(result){
                                 document.getElementById("hiragana").value = result;
                                       })
                                    .then(function () {
                                      return kuroshiro.convert(covertStringKanji, {mode:"furigana",to:"hiragana"});
                                       })
                                       .then(function(result){
                                          document.getElementById("kanjiRuby").value = result;
                                        alert("Done");
                     })


                   }

        </script>

    </form>


</@layout.mainLayout>