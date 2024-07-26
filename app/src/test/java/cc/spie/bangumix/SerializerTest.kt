package cc.spie.bangumix

import android.icu.text.IDNA.Info
import cc.spie.bangumix.data.models.InfoBoxItem
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.utils.providers.JsonProvider
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Test

class SerializerTest {

    @Test
    fun subjectDeserializeTest() {
        val jsonToDecode = "{\n" +
                "  \"date\": \"2024-07-06\",\n" +
                "  \"platform\": \"TV\",\n" +
                "  \"images\": {\n" +
                "    \"small\": \"https://lain.bgm.tv/r/200/pic/cover/l/32/14/424573_YYLzT.jpg\",\n" +
                "    \"grid\": \"https://lain.bgm.tv/r/100/pic/cover/l/32/14/424573_YYLzT.jpg\",\n" +
                "    \"large\": \"https://lain.bgm.tv/pic/cover/l/32/14/424573_YYLzT.jpg\",\n" +
                "    \"medium\": \"https://lain.bgm.tv/r/800/pic/cover/l/32/14/424573_YYLzT.jpg\",\n" +
                "    \"common\": \"https://lain.bgm.tv/r/400/pic/cover/l/32/14/424573_YYLzT.jpg\"\n" +
                "  },\n" +
                "  \"summary\": \"公元1333年，为日本的武士治国打下基础的镰仓幕府，\\r\\n因为深得信赖的幕臣·足立高氏的谋反而灭亡。\\r\\n\\r\\n失去一切、被推入绝望深渊的幕府正统继承人·北条时行，\\r\\n在自称是神的神官·诹访赖重的指引下逃出了化为焦土的镰仓……。\\r\\n\\r\\n时行在逃亡到诹访后，遇见了值得信赖的伙伴，并慢慢累积夺回镰仓所需的力量。\\r\\n面对时代变迁的惊涛骇浪，与「战斗」、「死亡」的武士生存之道背道而驰，\\r\\n时行选择以「逃跑」、「求生」克服困境。\\r\\n\\r\\n开始于这英雄众多的乱世，时行重新夺回天下的鬼抓人游戏将如何发展呢――。\",\n" +
                "  \"name\": \"逃げ上手の若君\",\n" +
                "  \"name_cn\": \"擅长逃跑的殿下\",\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"name\": \"CloverWorks\",\n" +
                "      \"count\": 430\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2024年7月\",\n" +
                "      \"count\": 355\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"漫画改\",\n" +
                "      \"count\": 272\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"TV\",\n" +
                "      \"count\": 230\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"历史\",\n" +
                "      \"count\": 205\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"梅原翔太\",\n" +
                "      \"count\": 181\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2024\",\n" +
                "      \"count\": 145\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"漫改\",\n" +
                "      \"count\": 133\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"山崎雄太\",\n" +
                "      \"count\": 95\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"正太\",\n" +
                "      \"count\": 79\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"搞笑\",\n" +
                "      \"count\": 54\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"松井優征\",\n" +
                "      \"count\": 27\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"中村悠一\",\n" +
                "      \"count\": 18\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2024年\",\n" +
                "      \"count\": 10\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"TVA\",\n" +
                "      \"count\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"战斗\",\n" +
                "      \"count\": 5\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"日本\",\n" +
                "      \"count\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"冨田頼子\",\n" +
                "      \"count\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"剧情\",\n" +
                "      \"count\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"奇幻\",\n" +
                "      \"count\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"未上映\",\n" +
                "      \"count\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"西谷泰史\",\n" +
                "      \"count\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"富田赖子\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2023\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"松井优征\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"結川あさき\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2024夏\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"24夏\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"男娘\",\n" +
                "      \"count\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"百合\",\n" +
                "      \"count\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"infobox\": [\n" +
                "    {\n" +
                "      \"key\": \"中文名\",\n" +
                "      \"value\": \"擅长逃跑的殿下\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"别名\",\n" +
                "      \"value\": [\n" +
                "        {\n" +
                "          \"v\": \"Nige Jouzu no Wakagimi\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"v\": \"The Elusive Samurai\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"v\": \"少主溜得快\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"话数\",\n" +
                "      \"value\": \"12\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"放送开始\",\n" +
                "      \"value\": \"2024年7月6日\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"放送星期\",\n" +
                "      \"value\": \"星期六\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"官方网站\",\n" +
                "      \"value\": \"https://nigewaka.run/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"播放电视台\",\n" +
                "      \"value\": \"TOKYO MX / BS11 / とちぎテレビ / 群馬テレビ\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"其他电视台\",\n" +
                "      \"value\": \"アニマックス / HTB北海道テレビ / IBC岩手放送 / テレビユー福島 / さくらんぼテレビ / 東日本放送 / 秋田朝日放送 / 青森放送 / テレビ新潟 / 中京テレビ / 北陸放送 / 静岡放送 / 信越放送 / チューリップテレビ / テレビ山梨 / MBS / 愛媛朝日テレビ / RSK山陽放送 / 広島テレビ / テレビ山口 / RKB毎日放送 / 長崎文化放送 / 宮崎放送 / 大分放送 / 鹿児島放送 / 琉球朝日放送 / 熊本放送\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"Copyright\",\n" +
                "      \"value\": \"©松井優征／集英社・逃げ上手の若君製作委員会\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"原作\",\n" +
                "      \"value\": \"松井優征（集英社「週刊少年ジャンプ」連載）\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"导演\",\n" +
                "      \"value\": \"山崎雄太\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"副导演\",\n" +
                "      \"value\": \"川上雄介\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"人物设定\",\n" +
                "      \"value\": \"西谷泰史；副：高橋沙妃\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"音乐\",\n" +
                "      \"value\": \"GEMBI、立山秋航\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"美术设计\",\n" +
                "      \"value\": \"taracod、takao\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"道具设计\",\n" +
                "      \"value\": \"よごいぬ(井上晴日)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"CG 导演\",\n" +
                "      \"value\": \"有沢包三、宮地克明\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"监修\",\n" +
                "      \"value\": \"历史监修：河合敦\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"建筑考证\",\n" +
                "      \"value\": \"鴎利一\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"字体设计\",\n" +
                "      \"value\": \"濱祐斗\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"主题歌编曲\",\n" +
                "      \"value\": \"新井弘毅 / ぼっちぼろまる\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"主题歌作曲\",\n" +
                "      \"value\": \"DISH// / ぼっちぼろまる\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"主题歌作词\",\n" +
                "      \"value\": \"北村匠海 / ぼっちぼろまる\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"主题歌演出\",\n" +
                "      \"value\": \"DISH// / ぼっちぼろまる\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"製作\",\n" +
                "      \"value\": \"逃げ上手の若君製作委員会（Aniplex、集英社、TOKYO MX、日本BS放送、CloverWorks）；製作协力：清水博之、奈良駿介、岡田武士、谷池侑美、松﨑由美子\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"企画\",\n" +
                "      \"value\": \"岩上敦宏、大好誠、佐藤真紀、田﨑勝也、清水暁；企画协力：齊藤優、小平哲也、本田佑行、桑原崇彰、頼富亮典\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"执行制片人\",\n" +
                "      \"value\": \"三宅将典、藤尾明史\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"总制片人\",\n" +
                "      \"value\": \"中山信宏\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"制片人\",\n" +
                "      \"value\": \"松本美穂、根本愛菜、長嶺利江子、大和田智之、福島祐一\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"助理制片人\",\n" +
                "      \"value\": \"大川はづき、橋本京佳、東真央\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"动画制片人\",\n" +
                "      \"value\": \"梅原翔太\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"音乐制作\",\n" +
                "      \"value\": \"Aniplex\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"音乐制作人\",\n" +
                "      \"value\": \"山内真治\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"协力\",\n" +
                "      \"value\": \"佐藤茂薫、月岡佑紀、佐藤顕\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"rating\": {\n" +
                "    \"rank\": 669,\n" +
                "    \"total\": 445,\n" +
                "    \"count\": {\n" +
                "      \"1\": 2,\n" +
                "      \"2\": 0,\n" +
                "      \"3\": 0,\n" +
                "      \"4\": 1,\n" +
                "      \"5\": 5,\n" +
                "      \"6\": 14,\n" +
                "      \"7\": 118,\n" +
                "      \"8\": 267,\n" +
                "      \"9\": 34,\n" +
                "      \"10\": 4\n" +
                "    },\n" +
                "    \"score\": 7.7\n" +
                "  },\n" +
                "  \"total_episodes\": 12,\n" +
                "  \"collection\": {\n" +
                "    \"on_hold\": 21,\n" +
                "    \"dropped\": 11,\n" +
                "    \"wish\": 663,\n" +
                "    \"collect\": 56,\n" +
                "    \"doing\": 2281\n" +
                "  },\n" +
                "  \"id\": 424573,\n" +
                "  \"eps\": 12,\n" +
                "  \"volumes\": 0,\n" +
                "  \"series\": false,\n" +
                "  \"locked\": false,\n" +
                "  \"nsfw\": false,\n" +
                "  \"type\": 2\n" +
                "}"

        val subject = JsonProvider.provideJson().decodeFromString(Subject.serializer(), jsonToDecode)
        println(subject)
    }

    @Test
    fun infoBoxDeserializeTest() {
        val testInfoBox = "[\n" +
                "    {\n" +
                "      \"key\": \"简体中文名\",\n" +
                "      \"value\": \"鲁路修·兰佩路基\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"别名\",\n" +
                "      \"value\": [\n" +
                "        {\n" +
                "          \"v\": \"L.L.\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"v\": \"勒鲁什\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"v\": \"鲁鲁修\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"v\": \"ゼロ\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"v\": \"Zero\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"k\": \"英文名\",\n" +
                "          \"v\": \"Lelouch Lamperouge\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"k\": \"第二中文名\",\n" +
                "          \"v\": \"鲁路修·冯·布里塔尼亚\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"k\": \"英文名二\",\n" +
                "          \"v\": \"Lelouch Vie Britannia\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"k\": \"日文名\",\n" +
                "          \"v\": \"ルルーシュ・ヴィ・ブリタニア\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"性别\",\n" +
                "      \"value\": \"男\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"生日\",\n" +
                "      \"value\": \"12月5日\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"血型\",\n" +
                "      \"value\": \"A型\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"身高\",\n" +
                "      \"value\": \"178cm→181cm\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"体重\",\n" +
                "      \"value\": \"54kg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"引用来源\",\n" +
                "      \"value\": \"Wikipedia\"\n" +
                "    }\n" +
                "  ]"

        val infoBox: List<InfoBoxItem> = JsonProvider.provideJson().decodeFromString(testInfoBox)
        println(infoBox)
    }
}