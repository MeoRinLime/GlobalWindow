<template>
  <div class="hotList">
    <div class="hotList-title">
      <n-tag style="font-size: large;" round :bordered="false" type="info" size="large">
        热点榜单
        <template #icon>
          <n-icon :component="Planet" />
        </template>
      </n-tag>
      <div class="types">
        <div
          v-for="(type, index) in types"
          :key="index"
          :class="['type-item', { typeActive: activeType === index }]"
          @click="changeType(index)"
        >
        <n-button strong type="warning" secondary round ghost>
          {{ type }}
          <template #icon>
            <n-icon :component="getIconComponent(type)" />
          </template>
        </n-button>
        </div>
      </div>
    </div>
    <div class="typeList">
      <button class="btn left" @click="prev">
        <el-icon><ArrowLeft /></el-icon>
      </button>
      <div class="list-cont-wrapper">
        <div class="list-cont" :style="{ transform: `translateX(-${activeType * (100 / visibleItems)}%)` }">
          <div
            v-for="(platform, index) in platforms"
            :key="index"
            class="re-group"
          >
            <div class="platform-header">
              <n-tag :bordered="false" type="info" round size="large">
                <n-icon :component="getIconComponent(platform.title)" />
                {{ platform.title }}
              </n-tag>
            </div>
            <div class="list-tile">
              <span class="re-hot-td rank">排名</span>
              <span class="re-hot-td hot">最高热度</span>
            </div>
            <ul class="topicList">
              <li
                v-for="(topic, topicIndex) in platform.topics"
                :key="topic.eventId"
                class="topic-item"
                @click="goToLink(topic.link)"
              >
                <div class="topic-left">
                  <span class="topic-num">{{ topicIndex + 1 }}</span>
                  <span class="topic-text" v-html="topic.news"></span>
                </div>
                <span class="topic-clout">{{ topic.popularity }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <button class="btn right" @click="next">
        <el-icon><ArrowRight /></el-icon>
      </button>
    </div>
    <n-button-group style="display: flex; margin: 0 auto; margin-bottom: 3%; align-items: flex-end; justify-content: center">
      <n-button ghost>
        其实我是一个按钮
      </n-button>
      <n-button ghost>
        我也是一个按钮
      </n-button>
      <n-button round>
        不要随便点奇怪的按钮哦
      </n-button>
    </n-button-group>
  </div>
</template>

<script>
import { ref, onMounted, nextTick } from 'vue';
import { ArrowLeft, ArrowRight } from '@element-plus/icons-vue';
import eventService from '../api/eventService';
import { Planet } from '@vicons/ionicons5';
import { Weibo, Book, Tiktok, Bars, Zhihu, Bold } from '@vicons/fa';

export default {
  props: {
    types: {
      type: Array,
      required: true,
    },
  },
  methods:{
    getIconComponent(type) {
      switch (type) {
        case '微博':
          return Weibo;
        case '小红书':
          return Book;
        case '今日头条':
          return Bars;  
        case '抖音':
          return Tiktok;
        case '知乎':
          return Zhihu; 
        case '百度':
          return Bold; 
        default:
          return Planet;
      }
    },
  },
  setup(props) {
    // const platforms = ref([]);
    const activeType = ref(1);  // 默认设置为1，因为数组前后将添加元素
    const visibleItems = ref(3);

    const platforms = ref([
  {
    title: "微博",
    topics: [
      {
        eventId: 1,
        news: "全球气候峰会召开，各国领导人齐聚一堂",
        popularity: 9500,
        link: "https://weibo.com/climate-summit"
      },
      {
        eventId: 2,
        news: "科技巨头发布新产品，引发市场热议",
        popularity: 8700,
        link: "https://weibo.com/tech-launch"
      },
      {
        eventId: 3,
        news: "国际电影节开幕，明星红毯秀惊艳全场",
        popularity: 7800,
        link: "https://weibo.com/film-festival"
      },
      {
        eventId: 4,
        news: "世界杯预选赛激烈进行，球迷热情高涨",
        popularity: 7200,
        link: "https://weibo.com/world-cup"
      },
      {
        eventId: 5,
        news: "全球股市大幅波动，投资者需谨慎",
        popularity: 6900,
        link: "https://weibo.com/stock-market"
      }
    ]
  },
  {
    title: "小红书",
    topics: [
      {
        eventId: 6,
        news: "秋季穿搭指南，时尚博主推荐必备单品",
        popularity: 6500,
        link: "https://xiaohongshu.com/fall-fashion"
      },
      {
        eventId: 7,
        news: "网红餐厅打卡，美食爱好者必去之地",
        popularity: 6200,
        link: "https://xiaohongshu.com/foodie-spot"
      },
      {
        eventId: 8,
        news: "旅行攻略分享，带你玩转小众景点",
        popularity: 5800,
        link: "https://xiaohongshu.com/travel-guide"
      },
      {
        eventId: 9,
        news: "美妆新品测评，性价比超高",
        popularity: 5500,
        link: "https://xiaohongshu.com/beauty-review"
      },
      {
        eventId: 10,
        news: "家居好物推荐，提升生活幸福感",
        popularity: 5200,
        link: "https://xiaohongshu.com/home-decor"
      }
    ]
  },
  {
    title: "今日头条",
    topics: [
      {
        eventId: 11,
        news: "国内油价调整，车主需注意",
        popularity: 5000,
        link: "https://toutiao.com/oil-price"
      },
      {
        eventId: 12,
        news: "教育改革新政策，家长热议",
        popularity: 4800,
        link: "https://toutiao.com/education-reform"
      },
      {
        eventId: 13,
        news: "新能源汽车销量创新高",
        popularity: 4600,
        link: "https://toutiao.com/new-energy-cars"
      },
      {
        eventId: 14,
        news: "房地产市场新动态，购房者关注",
        popularity: 4400,
        link: "https://toutiao.com/real-estate"
      },
      {
        eventId: 15,
        news: "科技行业招聘需求激增",
        popularity: 4200,
        link: "https://toutiao.com/tech-jobs"
      }
    ]
  },
  {
    title: "抖音",
    topics: [
      {
        eventId: 16,
        news: "热门舞蹈挑战，全网模仿",
        popularity: 4000,
        link: "https://douyin.com/dance-challenge"
      },
      {
        eventId: 17,
        news: "搞笑短视频合集，笑到停不下来",
        popularity: 3800,
        link: "https://douyin.com/funny-videos"
      },
      {
        eventId: 18,
        news: "宠物萌宠视频，治愈你的心情",
        popularity: 3600,
        link: "https://douyin.com/cute-pets"
      },
      {
        eventId: 19,
        news: "美食制作教程，简单易学",
        popularity: 3400,
        link: "https://douyin.com/cooking-tutorial"
      },
      {
        eventId: 20,
        news: "旅行Vlog，带你环游世界",
        popularity: 3200,
        link: "https://douyin.com/travel-vlog"
      }
    ]
  },
  {
    title: "知乎",
    topics: [
      {
        eventId: 21,
        news: "如何评价最新的科技产品？",
        popularity: 3000,
        link: "https://zhihu.com/tech-review"
      },
      {
        eventId: 22,
        news: "未来十年最有潜力的行业是什么？",
        popularity: 2800,
        link: "https://zhihu.com/future-industry"
      },
      {
        eventId: 23,
        news: "如何提升个人工作效率？",
        popularity: 2600,
        link: "https://zhihu.com/productivity"
      },
      {
        eventId: 24,
        news: "如何选择适合自己的职业？",
        popularity: 2400,
        link: "https://zhihu.com/career-choice"
      },
      {
        eventId: 25,
        news: "如何学习一门新技能？",
        popularity: 2200,
        link: "https://zhihu.com/learn-new-skill"
      }
    ]
  },
  {
    title: "百度",
    topics: [
      {
        eventId: 26,
        news: "百度发布最新AI技术，引领行业创新",
        popularity: 2000,
        link: "https://baidu.com/ai-tech"
      },
      {
        eventId: 27,
        news: "百度地图新增功能，出行更便捷",
        popularity: 1800,
        link: "https://baidu.com/map-update"
      },
      {
        eventId: 28,
        news: "百度搜索算法升级，用户体验提升",
        popularity: 1600,
        link: "https://baidu.com/search-algorithm"
      },
      {
        eventId: 29,
        news: "百度智能云助力企业数字化转型",
        popularity: 1400,
        link: "https://baidu.com/cloud-service"
      },
      {
        eventId: 30,
        news: "百度自动驾驶技术取得新突破",
        popularity: 1200,
        link: "https://baidu.com/autonomous-driving"
      }
    ]
  }
]);

    const fetchNewsData = async () => {
      try {
        const platformPromises = props.types.map(async (type) => {
          const response = await eventService.getHotEventBySource(type, 5);
          const topics = response.map((item) => ({
            eventId: item.eventId,
            news: item.event,
            popularity: item.popularity,
            link: item.link,
          }));
          return { title: type, topics };
        });

        const results = await Promise.all(platformPromises);
        // 无限滚动的准备：前后添加元素
        results.unshift(results[results.length - 1]);
        results.push(results[1]);
        platforms.value = results;
      } catch (error) {
        console.error('Error fetching news data:', error);
      }
    };

    onMounted(() => {
      fetchNewsData();
    });

    const changeType = (index) => {
      activeType.value = index;
    };

    const prev = () => {
      if (activeType.value > 1) {
        activeType.value--;
      } else {
        activeType.value = platforms.value.length - 2; // 跳到最后一个原始元素
        nextTick(() => {
          activeType.value--; // 然后动画到倒数第二个（真实的最后一个原始元素）
        });
      }
    };

    const next = () => {
      if (activeType.value < platforms.value.length - 2) {
        activeType.value++;
      } else {
        activeType.value = 1; // 跳回到第一个原始元素
        nextTick(() => {
          activeType.value++; // 然后动画到第二个（真实的第一个原始元素）
        });
      }
    };

    const goToLink = (link) => {
      if (link) {
        window.open(link, '_blank');
      }
    };

    return {
      Planet,
      platforms,
      activeType,
      visibleItems,
      changeType,
      prev,
      next,
      goToLink,
    };
  },
};
</script>


<style scoped>
.hotList {
  width: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 30px;
}
.hotList-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.title {
  font-size: 1.5em;
  font-weight: bold;
}
.upTime {
  font-size: 0.9em;
  color: grey;
}
.types {
  display: flex;
}
.type-item {
  margin: 0 5px;
  cursor: pointer;
  padding:0;
  border-radius: 1000px;
  transition: background-color 0.3s;
  border: 1px solid #e6e6e6;
}
.type-item:hover {
  background-color: transparent
}
.typeActive {
  font-weight: bold;
  background-color: rgba(32, 128, 240, 0.2);
}
.typeList {
  position: relative;
  width: 100%;
  overflow: hidden;
}
.line {
  height: 2px;
  background-color: #e6e6e6;
  margin-bottom: 10px;
}
.list-cont-wrapper {
  overflow: hidden;
  width: 100%;
}
.list-cont {
  display: flex;
  transition: transform 0.5s ease-in-out;
}
.re-group {
  flex: 0 0 calc(100% / 3); /* Each item takes 1/3rd of the container */
  box-sizing: border-box;
  padding: 10px;
}
.platform-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.platform-title {
  font-size: 1.2em;
  font-weight: bold;
}
.list-tile {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  font-weight: bold;
  background-color: #f2f2f2; /* Adding background for header */
  padding: 10px;
  border-radius: 5px;
}
.re-hot-td {
  flex: 1;
  text-align: center;
}
.rank {
  width: 50px;
  text-align: left;
}
.name {
  flex: 3;
  text-align: left; /* Align text to the left for better readability */
}
.hot {
  flex: 2;
  text-align: right; /* Align text to the right for better readability */
}
.topicList {
  list-style: none;
  padding: 0;
  margin: 0;
}

.topic-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 5px 0;
  padding: 10px;
  border-bottom: 1px solid #e6e6e6;
}

.topic-left {
  display: flex;
  align-items: center;
}

.topic-num {
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  background-color: rgb(236, 237, 248); /* Light blue background */
  color: rgb(85, 81, 152); /* Deep blue text */
  border-radius: 50%; /* Making the number circle */
  margin-right: 10px;
}

.topic-text {
  position: relative;
  cursor: pointer;
  margin: 0;
  flex: 1; /* Allow text to take remaining space */
  text-align: left; /* Align text to the left */
  transition: width 0.3s ease;
}

.topic-text::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: -2px; 
  width: 0;
  height: 2px;
  background-color: rgb(62, 90, 227);
  transition: width 0.3s ease;
}

.topic-text:hover::after {
  width: 100%;
}

.topic-clout {
  color: orange;
  display: flex;
  align-items: center;
}
.btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(255, 255, 255, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  z-index: 1;
}
</style>
