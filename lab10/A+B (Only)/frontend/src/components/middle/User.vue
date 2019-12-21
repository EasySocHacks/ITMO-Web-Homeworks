<template>
    <tr>
        <td class="id">{{user.id}}</td>
        <td class="login">{{user.login}}</td>
        <td class="creationTime">{{user.creationTime}}</td>
        <td class="creationTime">{{user.name}}</td>
    </tr>
</template>

<script>
    export default {
        props: ["user"],
        name: "User"
    }
</script>

<style scoped>

</style>