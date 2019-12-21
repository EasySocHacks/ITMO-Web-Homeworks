<template>
    <Posts :posts="posts"/>
</template>

<script>
    import Posts from "./Posts";
    export default {
        props: ["posts"],
        name: "Index",
        components: {Posts}
    }
</script>

<style scoped>

</style>
