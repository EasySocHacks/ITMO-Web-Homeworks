<template>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>

            <tr v-if="!users || users.length === 0" class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            <User v-for="user in sortedUsers" :user="user" v-bind:key="user.id"/>

        </tbody>
        </table>
    </div>
</template>

<script>
    import User from "./User";
    export default {
        components: {User},
        props: ["users"],
        name: "Users",
        computed: {
            sortedUsers: function () {
                return Object.values(this.users).sort((a, b) => a.id - b.id);
            }
        }
    }
</script>

<style scoped>

</style>