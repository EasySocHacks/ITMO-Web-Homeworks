<template>
    <div>
        <Post v-for="post in sortPosts" :post="post" v-bind:key="post.id"/>
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        components: {Post},
        props: ["posts"],
        name: "Posts",
        computed: {
            sortPosts: function () {
                return Object.values(this.posts).sort((a, b) => a.id - b.id);
            }
        }
    }
</script>

<style scoped>

</style>