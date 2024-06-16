# 弦轨智能陪练系统
# 软件文档

## 简介
这款软件是一款集音乐学习、训练和社区交流于一体的应用，旨在帮助用户通过多种方式提升音乐技能和知识。以下是软件的主要功能和界面介绍。

## 功能介绍

### 1. 首页
首页提供了个性化推荐和最新课程的展示。用户可以在此页面找到符合自己兴趣的音乐播放列表、私密漫游、每日推荐等。

- **个性推荐**：根据用户的喜好推荐每日歌曲和播放列表。
- **推荐课程**：根据用户的学习进度推荐相关的音乐课程。
- **训练入口**：快速进入音乐训练模块。

![首页](file-kyamX1CXrG9weekNO5Bzeef3)

### 2. 乐库
乐库页面提供了丰富的音乐资源，并根据曲风进行分类，用户可以轻松找到自己喜欢的音乐类型。

- **为您推荐**：根据用户的喜好推荐精选歌曲。
- **曲风分类**：提供摇滚、乡村、世界音乐、民谣、流行、轻音乐、说唱、爵士等多种音乐类型。

![乐库](file-WchxvSbotzhpuAm08cfqWUTO)

### 3. 课程
课程页面展示了用户已购买和正在进行的音乐课程，用户可以在此查看课程的详细信息和学习进度。

- **全部**：显示所有课程，包括已购买和免费课程。
- **进行中**：展示用户当前正在学习的课程及其进度。
- **已完成**：展示用户已完成的课程。

![课程](file-4rNezQ4MMGSY8ImDnlDxRnqj)

### 4. 训练记录
训练记录页面记录了用户的每次音乐训练情况，包括训练时长、音准评分、视频评分等，并提供改进建议。

- **训练记录查询**：用户可以查看并搜索所有的训练记录。
- **训练评分**：详细展示每次训练的音准、节奏、技巧评分，并提供整体评价。

![训练记录](file-A0mir1ssUlkbInmWcdz6pZjR)

### 5. 训练分析
训练分析页面提供了演奏词云总览、各和弦演奏质量统计和演奏分析报告，帮助用户深入了解自己的演奏情况并进行改进。

- **演奏词云总览**：通过词云图展示用户常用的和弦和演奏情况。
- **各和弦演奏质量统计**：统计并分析用户在不同和弦上的演奏质量。
- **演奏分析报告**：提供详细的演奏分析报告，帮助用户发现问题并改进。

![训练分析](file-4pZwFBqrblNo2ACB4wKN12cI)

### 6. 音乐播放
音乐播放页面提供了简单易用的音乐播放功能，用户可以播放、暂停、快进和回退音乐，并查看当前播放的曲目信息。

![音乐播放](file-BlrtaiaxzB0orhTDMX1M8O96)

### 7. 社区
社区页面是用户交流互动的地方，用户可以在这里发布动态、观看直播、参与讨论等，提升学习的互动性和趣味性。

- **广场**：展示所有用户的动态和热门内容。
- **关注**：展示用户关注的人的动态。
- **发布**：用户可以发布自己的音乐相关动态。

![社区](file-p8v5W1LA2lF4AbZ5mvU2IzHo)

### 8. 个人中心
个人中心页面展示了用户的基本信息、VIP等级、课程收藏、意见反馈等，用户可以在此管理自己的账户和设置。

- **我的课程**：查看和管理已购买的课程。
- **我的收藏**：查看和管理收藏的内容。
- **账户设置**：包括意见反馈、用户协议、隐私政策等。

![个人中心](file-cZu8VqoLleQN1WIqRbBNM3rr)

### 9. 评分
评分页面展示了用户每次训练后的评分和评价，包括音准、节奏、技巧等方面的详细评分，帮助用户了解自己的进步情况。

![评分](file-iMA4F7Vy88axS9J9pdOwRfH1)

## 依赖
'''
dependencies {

    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation 'com.github.wendykierp:JTransforms:3.1'

}
'''
