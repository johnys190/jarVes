import React, { Component } from 'react';
import { getAvatarColor } from '../../util/Colors';
import { formatDate } from '../../util/Helpers';
import LoadingIndicator  from '../../common/LoadingIndicator';
import './Profile.css';
import NotFound from '../../common/NotFound';
import ServerError from '../../common/ServerError';
import { signup, checkUsernameAvailability, checkEmailAvailability } from '../../util/APIUtils';
import { 
    NAME_MIN_LENGTH, NAME_MAX_LENGTH, 
    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH,
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH
} from '../../constants';
import { 
    getUserById,
    updateUserById
 } from '../../util/APIUtils';
import { Avatar, Tabs, Form, Button, Input, notification } from 'antd';

const FormItem = Form.Item;

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                id: this.props.id
                // id: this.props.match.params.id ? this.props.match.params.id : null
            },
            isLoading: false
        }
        this.loadUserProfile = this.loadUserProfile.bind(this);
        console.log(this.props);
        this.loadUserProfile();
    }

    loadUserProfile() {
        this.setState({
            isLoading: true
        });

        getUserById(this.state.user.id)
        .then(response => {
            this.setState({
                user: response,
                isLoading: false
            });
        }).catch(error => {
            if(error.status === 404) {
                this.setState({
                    notFound: true,
                    isLoading: false
                });
            } else {
                this.setState({
                    serverError: true,
                    isLoading: false
                });        
            }
        });        
    }
      
    // componentDidMount() {
    //     const id = this.props.match.params.id;
    //     this.loadUserProfile(id);
    // }

    // componentDidUpdate(nextProps) {
    //     if(this.props.match.params.id !== nextProps.match.params.id) {
    //         this.loadUserProfile(nextProps.match.params.id);
    //     }        
    // }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;
        const userEdit = this.state.user;
        userEdit[inputName]= inputValue;
        this.setState({
            user:userEdit
        });
    }

    handleSubmit(event) {
        // event.preventDefault();
    
        // const signupRequest = {
        //     name: this.state.firstName.value,
        //     email: this.state.email.value,
        //     lastName: this.state.lastName.value,
        //     password: this.state.password.value
        // };
        // updateUserById(signupRequest)
        // .then(response => {
        //     notification.success({
        //         message: 'Polling App',
        //         description: "Thank you! You're successfully registered. Please Login to continue!",
        //     });          
        //     this.props.history.push("/login");
        // }).catch(error => {
        //     notification.error({
        //         message: 'Polling App',
        //         description: error.message || 'Sorry! Something went wrong. Please try again!'
        //     });
        // });

        this.setState({
            isLoading: true,
        });

        let promise = updateUserById(this.state.user.id, this.state.user);
        promise
            .then(response => {
                notification.success({
                    message: 'Link Line Voyage Estimate',
                    description: "Sucessfully saved changes!",
                });
                this.setState({
                    isLoading: false
                });
            })
            .catch(error => {
                notification.error({
                    message: 'Link Line Voyage Estimate',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
                this.setState({
                    isLoading: false
                });
            });
    }

    isFormInvalid() {
        return !(this.state.firstName.validateStatus === 'success' &&
            this.state.lastName.validateStatus === 'success' &&
            this.state.email.validateStatus === 'success' &&
            this.state.password.validateStatus === 'success'
        );
    }

    // Validation Functions

    validateName = (name) => {
        if(name.length < NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Name is too short (Minimum ${NAME_MIN_LENGTH} characters needed.)`
            }
        } else if (name.length > NAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Name is too long (Maximum ${NAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
              };            
        }
    }

    validateEmail = (email) => {
        if(!email) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email may not be empty'                
            }
        }

        const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
        if(!EMAIL_REGEX.test(email)) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email not valid'
            }
        }

        if(email.length > EMAIL_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Email is too long (Maximum ${EMAIL_MAX_LENGTH} characters allowed)`
            }
        }

        return {
            validateStatus: null,
            errorMsg: null
        }
    }

    validateUsername = (lastName) => {
        if(lastName.length < USERNAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Last Name is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`
            }
        } else if (lastName.length > USERNAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Last Name is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    }

    validateUsernameAvailability() {
        // First check for client side errors in lastName
        const lastNameValue = this.state.lastName.value;
        const lastNameValidation = this.validateUsername(lastNameValue);

        if(lastNameValidation.validateStatus === 'error') {
            this.setState({
                lastName: {
                    value: lastNameValue,
                    ...lastNameValidation
                }
            });
            return;
        }

        this.setState({
            lastName: {
                value: lastNameValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkUsernameAvailability(lastNameValue)
        .then(response => {
            if(response.available) {
                this.setState({
                    lastName: {
                        value: lastNameValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            } else {
                this.setState({
                    lastName: {
                        value: lastNameValue,
                        validateStatus: 'error',
                        errorMsg: 'This lastName is already taken'
                    }
                });
            }
        }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                lastName: {
                    value: lastNameValue,
                    validateStatus: 'success',
                    errorMsg: null
                }
            });
        });
    }

    validateEmailAvailability() {
        // First check for client side errors in email
        const emailValue = this.state.email.value;
        const emailValidation = this.validateEmail(emailValue);

        if(emailValidation.validateStatus === 'error') {
            this.setState({
                email: {
                    value: emailValue,
                    ...emailValidation
                }
            });    
            return;
        }

        this.setState({
            email: {
                value: emailValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkEmailAvailability(emailValue)
        .then(response => {
            if(response.available) {
                this.setState({
                    email: {
                        value: emailValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            } else {
                this.setState({
                    email: {
                        value: emailValue,
                        validateStatus: 'error',
                        errorMsg: 'This Email is already registered'
                    }
                });
            }
        }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                email: {
                    value: emailValue,
                    validateStatus: 'success',
                    errorMsg: null
                }
            });
        });
    }

    validatePassword = (password) => {
        if(password.length < PASSWORD_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };            
        }
    }

    render() {
        if(this.state.isLoading) {
            return <LoadingIndicator />;
        }

        if(this.state.notFound) {
            return <NotFound />;
        }

        if(this.state.serverError) {
            return <ServerError />;
        }

        const tabBarStyle = {
            textAlign: 'center'
        };

        return (
            <div className="signup-container">
                <h1 className="page-title">Modify</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem 
                            //label="Full Name"
                            //validateStatus={this.state.firstName.validateStatus}
                            //help={this.state.firstName.errorMsg}
                            >
                            <Input 
                                size="large"
                                name="name"
                                autoComplete="off"
                                placeholder="Your name"
                                value={this.state.user.name} 
                                onChange={(event) => this.handleInputChange(event, this.validateName)} />    
                        </FormItem>
                        <FormItem label="Last Name"
                            //hasFeedback
                            //validateStatus={this.state.lastName.validateStatus}
                            //help={this.state.lastName.errorMsg}
                            >
                            <Input 
                                size="large"
                                name="lastName" 
                                autoComplete="off"
                                placeholder="Last Name"
                                value={this.state.user.lastName} 
                                onBlur={this.validateUsernameAvailability}
                                onChange={(event) => this.handleInputChange(event, this.validateUsername)} />    
                        </FormItem>
                        <FormItem label="Email"
                            // hasFeedback
                            // validateStatus={this.state.email.validateStatus}
                            // help={this.state.email.errorMsg}
                            >
                            <Input 
                                size="large"
                                name="email" 
                                type="email" 
                                autoComplete="off"
                                placeholder="Your email"
                                value={this.state.user.email} 
                                onBlur={this.validateEmailAvailability}
                                onChange={(event) => this.handleInputChange(event, this.validateEmail)} />    
                        </FormItem>
                        <FormItem 
                            label="Password"
                            // validateStatus={this.state.password.validateStatus}
                            // help={this.state.password.errorMsg}
                            >
                            <Input 
                                size="large"
                                name="password" 
                                type="password"
                                autoComplete="off"
                                placeholder="A password between 6 to 20 characters" 
                                value={this.state.user.password} 
                                onChange={(event) => this.handleInputChange(event, this.validatePassword)} />    
                        </FormItem>
                        <FormItem>
                            <Button type="primary" 
                                //htmlType="submit" 
                                onClick={this.handleSubmit.bind(this)}
                                size="large" 
                                className="signup-form-button"
                                //isabled={this.isFormInvalid()}
                                >
                                Save
                            </Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }
}

export default Profile;