<template>
  <n-layout style="padding-left: 5%; padding-right: 5%; height: 100vh " embedded>
    <n-layout has-sider style="height: 100%">
      <n-layout-sider 
        bordered
        width="15vw" 
        style="min-width: 200px; max-width: 300px">
        <n-menu
          v-model:value="selectedCategory"
          @update:value="handleSelect"
          vertical
          :options="menuOptions"
          style="height: 100%; overflow: auto; font-size: 20px">
        </n-menu>
      </n-layout-sider>
      <n-layout-content :native-scrollbar="false">
        <div v-for="event in processedEvents" :key="event.newsId">
          <n-card embedded hoverable style="margin-bottom: 16px; max-width: 95%; margin-right: auto; margin-left: auto">
            <template #header>
              <div>
                <span class="rank-circle">{{ event.rank }}</span>
                {{ event.title }}
              </div>
            </template>
            <div>
              <p v-html="event.cleanedNews"></p>
              <n-button style="width:auto; padding-left: 15px; padding-right: 15px;" strong secondary circle type="success" @click="goToNewsDetails(event.newsId)">查看详情</n-button>
              <n-button style="margin-left: 2%; width: auto; padding-left: 15px; padding-right: 15px;" type="error" circle ghost class="event-popularity">热度指数: {{ event.popularity }}</n-button>
            </div>
          </n-card>
        </div>
      </n-layout-content>
    </n-layout>
  </n-layout>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import router from '../router';
import { NIcon } from 'naive-ui'
import newsService from '../api/newsService'; // 导入配置好的 newsService 实例
import mainIcon from '../components/icons/mainIcon.vue';
import policyIcon from '../components/icons/policyIcon.vue';
import economyIcon from '../components/icons/economyIcon.vue';
import playIcon from '../components/icons/playIcon.vue';
import scienceIcon from '../components/icons/scienceIcon.vue';
import whuIcon from '../components/icons/whuIcon.vue';
import otherIcon from '../components/icons/otherIcon.vue';

export default {
  name: 'HotSpot',
  setup() {
    const selectedCategory = ref('综合');
    // const events = ref([]);
    const categories = ['综合', '社会新闻', '经济', '娱乐', '科技', '武大', '其他'];
    const events = ref([
      {
        newsId: 101,
        news: "#武汉樱花季# 今年预计接待游客超百万，校园实行预约制 #文旅#",
        popularity: 8754,
        category: "武大"
      },
      {
        newsId: 102,
        news: "#新能源汽车补贴# 财政部宣布延长购置税减免政策 #行业政策#",
        popularity: 9213,
        category: "经济"
      },
      {
        newsId: 103,
        news: "#量子计算新突破# 我校团队实现量子比特稳定性新纪录 #科研进展#",
        popularity: 7986,
        category: "科技"
      },
      {
        newsId: 104,
        news: "#校园食堂升级# 梅园食堂引入智能结算系统 #后勤改革#",
        popularity: 6542,
        category: "武大"
      },
      {
        newsId: 105,
        news: "#人工智能伦理# 多所高校联合发布AI发展白皮书 #学术合作#",
        popularity: 7321,
        category: "综合"
      },
      {
        newsId: 106,
        news: "#珞珈讲坛# 诺贝尔奖得主将于本周五线上开讲 #学术交流#",
        popularity: 8876,
        category: "武大"
      },
      {
        newsId: 107,
        news: "#光谷实验室# 新型半导体材料研发取得阶段性成果 #科研突破#",
        popularity: 9432,
        category: "科技"
      },
      {
        newsId: 108,
        news: "#校园马拉松# 报名人数突破3000人创历史新高 #体育赛事#",
        popularity: 6721,
        category: "武大"
      },
      {
        newsId: 109,
        news: "#虚拟偶像演唱会# 全息技术打造沉浸式舞台 #数字娱乐#",
        popularity: 8214,
        category: "娱乐"
      },
      {
        newsId: 110,
        news: "#碳交易市场# 全国碳排放权交易量环比增长27% #绿色经济#",
        popularity: 7654,
        category: "经济"
      },
      {
        newsId: 111,
        news: "#暴雨红色预警# 市政启动防汛应急预案 #城市安全#",
        popularity: 9987,
        category: "社会新闻"
      },
      {
        newsId: 112,
        news: "#古籍数字化# 图书馆完成明刻本电子化工程 #文化传承#",
        popularity: 5897,
        category: "其他"
      },
      {
        newsId: 113,
        news: "#AI助教系统# 计算机学院试点智能教学辅助 #教育创新#",
        popularity: 8543,
        category: "综合"
      },
      {
        newsId: 114,
        news: "#量子通信# 联合实验室实现城际密钥分发 #前沿科技#",
        popularity: 9123,
        category: "科技"
      },
      {
        newsId: 115,
        news: "#校友捐赠# 生物医药校友设立千万奖学金 #感恩回馈#",
        popularity: 7012,
        category: "武大"
      }
    ]);

    function renderIcon (icon) {
      return () => h(NIcon, null, { default: () => h(icon) })
    }

    // 正则表达式来匹配第一个被"#"包围的词
    function findFirstHashTag(htmlString) {
      const hashtagRegex = /#([^#]+)#/;
      const match = htmlString.match(hashtagRegex);
      return match ? match[1] : null;
    }

    // 去除所有被"#"包围的数据
    function removeHashTags(htmlString) {
      return htmlString.replace(/#[^#]+#/g, '').trim();
    }

    const processedEvents = computed(() => {
      return events.value.map((event, index) => ({
        ...event,
        rank: index + 1, // 保持排名为单独的属性
        title: findFirstHashTag(event.news), // 仅标题文本
        cleanedNews: removeHashTags(event.news)
      }));
    });

    const menuOptions = computed(() => {
      return categories.map((category, index) => ({
        label: category,
        key: category,
        icon: (() => {
          const icons = [
            renderIcon(mainIcon),
            renderIcon(policyIcon),
            renderIcon(economyIcon),
            renderIcon(playIcon),
            renderIcon(scienceIcon),
            renderIcon(whuIcon),
            renderIcon(otherIcon)
          ];
          return icons[index];
        })()
      }));
    });

    const filteredEvents = computed(() => {
      return events.value.filter(event => event.category === selectedCategory.value);
    });

    const handleSelect = async (value) => {
      selectedCategory.value = value;
      await fetchEventsByType(selectedCategory.value);
    };

    const fetchEventsByType = async (category) => {
      try {
        let response;
        if (category === '综合') {
          response = await newsService.getNewsByPopularity(5);
        } else {
          response = await newsService.getNewsByType(5, category);
        }
        events.value = response.map(item => ({ ...item, category }));
      } catch (error) {
        console.error('获取事件数据时出错:', error);
        events.value = [];
      }
    };

    const goToNewsDetails = (newsId) => {
      router.push({
        name: 'newsDetails',
        params: { newsId }
      });
    };

    onMounted(() => {
      // fetchEventsByType(selectedCategory.value);
    });

    return { processedEvents, selectedCategory, events, filteredEvents, handleSelect, goToNewsDetails, categories, menuOptions };
  }
};
</script>

<style scoped>

.n-layout {
  height: 100vh; /* 使布局填满视窗高度 */
}

.rank-circle {
  background-color: #ff691e;
  color: white;
  border-radius: 100%;
  padding: 5px 12px;
  font-size: 1.2em;
}

.n-layout-content {
  overflow-y: auto;
}
</style>
