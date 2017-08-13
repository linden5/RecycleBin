<template>
  <div class="col border-basic tab-container">
      <h4 class="tab-title">{{title}}</h4>
      <h5 class="tab-sub-title">{{sub}}</h5>
      <div class="tab-stat">{{stats}}</div>
  </div>
</template>

<script>
export default {
  props: ['title', 'sub', 'stats']
}
</script>


<style>
.tab-title {
    margin: 0;
    font-size: .9rem;
    color: #555;
}

.tab-sub-title {
    margin: 0;
    font-size: .8rem;
}

.tab-stat {
    color: #333;
    font-size: 1.5rem;
}

.tab-container {
    padding: .5rem;
}
</style>


