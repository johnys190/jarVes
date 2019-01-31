import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Menu.css';
import { 
    Form,
    Input,
    Button,
    Row,
    Col,
    notification
} from 'antd';
import { renderID } from '../util/Helpers';
import { 
    getVesselById,
    createVessel,
    updateVesselById
} from '../util/APIUtils';

const FormItem = Form.Item;
const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      }
};


class Vessel extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            vessel:{
                id: this.props.id ? this.props.id : 'new',
                boiler: '',
                built: '',
                dwt: '',
                flag: '',
                gear: '',
                grain: '',
                ifo_ballast: '',
                ifo_laden: '',
                manager: '',
                mgo_port_idle: '',
                mgo_port_working: '',
                name: '',
                pic: '',
                port_idle: '',
                port_working: '',
                speed: '',
                type: ''
            }
        };

        if (this.state.vessel.id !== 'new'){
            this.getVessel();
        }
    }

    getVessel(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getVesselById(this.state.vessel.id);

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    vessel:response,
                    isLoading: false
                })
            }).catch(error => {
                this.setState({
                    isLoading: false
            })
        });
    }

    handleSubmit() {
        this.setState({
            isLoading: true,
        });

        let vessel = this.state.vessel;
        let promise;
        if (this.state.vessel.id !== 'new'){

            promise = updateVesselById(this.state.vessel.id, vessel);
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
        }else{
            delete vessel['id'];
            promise = createVessel(vessel);
            promise
                .then(response => {
                    notification.success({
                        message: 'Link Line Voyage Estimate',
                        description: "Sucessfully created Vessel!",
                    });
                    this.setState({
                        vessel: response,
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
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;
        const vesselEdit = this.state.vessel;
        vesselEdit[inputName]= inputValue;
        this.setState({
            vessel:vesselEdit
        });
    }

    renderFormItemList(fields){

        return fields.map( (field) => (
            <FormItem {...formItemLayout}> 
                <Input name={renderID(field)} onChange={(event) => this.handleInputChange(event)} type='number' step='any' addonBefore={field} className='alignComponent' defaultValue={this.state.vessel[renderID(field)]}/>
            </FormItem>
        ));
    }

	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }
		return (
				<div>
                    <Form onSubmit={this.handleSubmit.bind(this)}>  
                        <Row gutter={15}>
                            <Col  xs={24} sm={24} md={24} lg={24} xl={12}>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Name')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore= 'Name' defaultValue={this.state.vessel[renderID('Name')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('DWT')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='DWT' type='number' step='any' defaultValue={this.state.vessel[renderID('DWT')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Type')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Type' defaultValue={this.state.vessel[renderID('Type')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Flag')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Flag' defaultValue={this.state.vessel[renderID('Flag')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Built')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Built' type='number' step='any' defaultValue={this.state.vessel[renderID('Built')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Gear')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Gear' defaultValue={this.state.vessel[renderID('Gear')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Grain')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Grain' type='number' step='any' defaultValue={this.state.vessel[renderID('Grain')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Manager')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Manager' defaultValue={this.state.vessel[renderID('Manager')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}> 
                                    <Input name={renderID('Pic')} onChange={(event) => this.handleInputChange(event)} className='alignComponent' addonBefore='Pic' defaultValue={this.state.vessel[renderID('Pic')]}/>
                                </FormItem>
                                <FormItem {...formItemLayout}>
                                    <span class="ant-input-group-wrapper">
                                        <span class="ant-input-wrapper ant-input-group">
                                            <span class="ant-input-group-addon">
                                                Note
                                            </span>
                                            <Input.TextArea name='note' onChange={(event) => this.handleInputChange(event)} className='alignSelect' addonBefore='d' autosize={{minRows: 3, maxRows: 8 }} />
                                        </span>
                                    </span>
                                </FormItem>
                            </Col>
                            <Col  xs={24} sm={24} md={24} lg={24} xl={12}>
                                {this.renderFormItemList(['Speed', 'Ifo-Ballast', 'Ifo-Laden', 'Port Idle', 'Port Working', 'MGO Port Idle', 'MGO Port Working', 'Boiler'])}
                                <Row gutter={15} type='flex' justify='start'>
                                    <Col offset={4} xs={8} sm={8} md={8} lg={6} xl={6}>  
                                        <FormItem>                              
                                            <Button type='primary' htmlType="submit">Save</Button>
                                        </FormItem>
                                    </Col>
                                    <Col xs={8} sm={8} md={8} lg={6} xl={6}>  
                                        <Button type='danger' href="/">Cancel</Button>
                                    </Col>
                                </Row>
                                <br />
                            </Col>
                        </Row>
                    </Form>
                </div>
			);
	}
}
export default withRouter(Vessel);

