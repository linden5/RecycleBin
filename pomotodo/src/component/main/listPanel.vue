<template>
    <div class="list-panel">
        <h3 class="list-panel-title">
            <slot name="header"></slot>
        </h3>
        <div class="list-panel-list">
            <slot name="list"></slot>
        </div>
    </div>
</template>

<style lang="scss">
.list-panel {
    padding: 1em;
    border: 1px solid #ddd;
    border-radius: 5px;
    &:last-child {
        margin-left: 1em;
    }
}

.list-panel-title {
    padding-bottom: 1em;
    border-bottom: 1px solid #ddd;
}
</style>

