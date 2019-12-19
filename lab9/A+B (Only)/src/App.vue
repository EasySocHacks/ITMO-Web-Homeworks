<template>
    <!--suppress HtmlUnknownTag -->
    <body id="app">
    <Header :userId="userId" :users="users"/>
    <Middle :users="users" :posts="posts"/>
    <Footer/>
    </body>
</template>

<script>
    import Header from './components/Header'
    import Middle from './components/Middle'
    import Footer from './components/Footer'

    export default {
        name: 'app',
        data: function () {
            return this.$root.$data;
        },
        components: {
            Header,
            Middle,
            Footer
        }, beforeCreate() {
            this.$root.$on("onLogout", () => {
                this.userId = null;
            });
            this.$root.$on("onEnter", (login) => {
                let users = Object.values(this.users).filter(u => u.login === login);
                if (users.length) {
                    this.userId = users[0].id;
                    this.$root.$emit("onEnterSuccess");
                } else {
                    this.$root.$emit("onEnterValidationError", "Invalid login/password.");
                }
            });
            this.$root.$on("onAddPost", (title, text) => {
                if (this.userId) {
                    if (!title || title.length > 5) {
                        this.$root.$emit("onAddPostValidationError", "Title is invalid");
                    } else if (!text || text.length > 10) {
                        this.$root.$emit("onAddPostValidationError", "Text is invalid");
                    } else {
                        const id = Math.max(...Object.keys(this.posts)) + 1;
                        this.$set(this.posts, id, {
                            id,
                            userId: this.userId,
                            title,
                            text
                        })
                    }
                } else {
                    this.$root.$emit("onAddPostValidationError", "No access");
                }
            });
            this.$root.$on("onEditPost", (id, text) => {
                if (this.userId) {
                    if (!id) {
                        this.$root.$emit("onEditPostValidationError", "ID is invalid");
                    } else if (!text || text.length > 10) {
                        this.$root.$emit("onEditPostValidationError", "Text is invalid");
                    } else {
                        let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                        if (posts.length) {
                            posts.forEach((item) => {
                                item.text = text;
                            });
                        } else {
                            this.$root.$emit("onEditPostValidationError", "No such post");
                        }
                    }
                } else {
                    this.$root.$emit("onEditPostValidationError", "No access");
                }
            });

            this.$root.$on("onRegister", (login, name) => {
                if (!this.userId) {
                    if (!login || login.length < 3 || login.length > 16 || !login.match("\\w+")) {
                        this.$root.$emit("onRegisterValidationError", "Login must contains 3-16 latin characters");
                    } else if (!name || name.length < 1 || name.length > 32) {
                        this.$root.$emit("onRegisterValidationError", "Name must contains 1-32 characters");
                    } else {
                        const foundUsers = Object.values(this.users).filter(u => u.login === login);

                        if (foundUsers.length) {
                            this.$root.$emit("onRegisterValidationError", "Login is already in use");
                        } else {
                            const id = Math.max(...Object.keys(this.users)) + 1;
                            this.$set(this.users, id, {
                                id,
                                login,
                                name,
                                admin: false
                            });

                            this.$root.$emit("onChangePage", "Index");
                        }
                    }
                } else {
                    this.$root.$emit("onRegisterValidationError", "You have to be logged out to register");
                }
            });
        }
    }
</script>

<style>
</style>
