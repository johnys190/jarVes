import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Menu.css';
import { renderID } from '../util/Helpers';
import {
    Input,
    DatePicker,
    Select,
    Button,
    notification
} from 'antd';
import {
    getTCEstimateById,
    updateTCEstimateById,
    deleteTCEstimateById,
    createTCEstimate
} from '../util/APIUtils';

const Option = Select.Option;

class TCEstimate extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            tcest:{
                id: this.props.id ? this.props.id : 'new',
                account: null,
                approx_dur: null,
                ballast_bonus: null,
                ballast_distance_non_seca: null,
                ballast_distance_seca: null,
                bb_gross: null,
                broker: null,
                bunker_cost: null,
                canals_cost: null,
                commision_percent: null,
                commodity: null,
                date: null,
                delivery_costs: null,
                expenses: null,
                gross_revenue: null,
                hire_rate: null,
                ifo_price: null,
                laden_distance_non_seca: null,
                laden_distance_seca: null,
                laycan: null,
                lost_waiting_days: null,
                mdo_price: null,
                miscel_costs: null,
                name: null,
                net_revenue: null,
                redelivery_costs: null,
                reposition: null,
                sensitivity: null,
                sensitivity_five_days: null,
                time_charter_rate: null,
                total_duration: null,
                vessel: null,
                voyage: null
            }

        };
        if (this.state.tcest.id !== 'new'){
            this.getTCEstimate();
        }
    }

    calculate(){
        // if (this.state.hirerate && this.state.ballastbonus){
        //     this.setState({
        //         timecharterrate:  Number(this.state.hirerate) + Number(this.state.ballastbonus)
        //     })
        // }
    }

    getTCEstimate(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getTCEstimateById(this.state.tcest.id);

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    tcest:response,
                    isLoading: false
                });
                console.log(this.state);
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

        let tcest = this.state.tcest;
        let promise;
        console.log(this.state);
        if (this.state.tcest.id !== 'new'){

            promise = updateTCEstimateById(this.state.tcest.id, tcest);
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
            delete tcest['id'];
            promise = createTCEstimate(tcest);
            promise
                .then(response => {
                    notification.success({
                        message: 'Link Line Voyage Estimate',
                        description: "Sucessfully created!",
                    });

                    this.setState({
                        tcest: response,
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
        console.log(this.state);
    }

    handleSubmitAs() {
        this.setState({
            isLoading: true,
        });

        let tcest = this.state.tcest;
        let curName = tcest.name;
        tcest.name = tcest.save_as;
        delete tcest['id'];
        let promise;
        promise = createTCEstimate(tcest);
        promise
            .then(response => {
                notification.success({
                    message: 'Link Line Voyage Estimate',
                    description: "Sucessfully saved!",
                });
                this.setState({
                    tcest: response,
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

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;
        const tcestEdit = this.state.tcest;
        tcestEdit[inputName]= inputValue;
        this.setState({
            tcest:tcestEdit
        });
    }

    // handleChangeInput = (e) => {
    //     const id = e.target.id;
    //     const value = e.target.value;
    //     this.setState({
    //         [id]: value
    //         },
    //         this.calculate
    //     );
    // }

    handleChangeSelect = (e) => {
        this.setState({
            voyage: e
        })
    }

    handleNewCancelClick = () => {
        this.setState({
            new: !this.state.new
        });
    }

    handleSave = () => {
        console.log(this.state);
    }

    renderInputList(fields, className='alignComponent'){
        return fields.map( (field) => (
             <Input  
                className={className}
                addonBefore={field} 
                name={renderID(field)}
                onChange={this.handleInputChange.bind(this)}
                defaultValue={this.state.tcest[renderID(field)]}/> 
        ))
    }
    renderInputListDisabled(fields, className='alignComponent'){
        return fields.map( (field) => (
             <Input  
                className={className}
                addonBefore={field} 
                name={renderID(field)}
                onChange={this.handleInputChange.bind(this)}
                disabled={true}
                value={this.state.tcest[renderID(field)]}/> 
        ))
    }



	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }

        const saveAs = (this.state.tcest.id && this.state.tcest.id !== 'new') ?
            (<div>
                <Input addonBefore='Save as' name='save_as'  onChange={(event) => this.handleInputChange(event)} defaultValue={this.state.tcest.save_as}/>
                <Button className='button' type='primary' onClick={this.handleSubmitAs.bind(this)}>Save as</Button>
            </div>) : '';

		return (
            <div>

                <div className='alignLeft'>
                    <Input addonBefore='Name' name='name' defaultValue={this.state.tcest.name}  onChange={(event) => this.handleInputChange(event)}/>
                    <Button className='button' type='primary' onClick={this.handleSubmit.bind(this)}>Save</Button>
                    {saveAs}
                    <br />
                    <br />
                    <span class="ant-input-group-wrapper">
                        <span class="ant-input-wrapper ant-input-group">
                            <span class="ant-input-group-addon">
                                Vessel
                            </span>
                            <Select
                                className='alignSelect'
                                showSearch
                                placeholder="Select a vessel"
                                optionFilterProp="children"
                                onChange={this.handleChange}
                                onFocus={this.handleFocus}
                                onBlur={this.handleBlur}
                                filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
                                    <Option value="jack">Jack</Option>
                                    <Option value="lucy">Lucy</Option>
                                    <Option value="tom">Tom</Option>
                            </Select>
                        </span>
                    </span>
                    
                    <br />
                    <div className='alignLeft'>
                        {this.renderInputList(['Voyage', 'Account', 'Commodity', 'Broker'])}
                    </div>
                    <div className='alignRight'>
                        {this.renderInputList(['Laycan', 'Repos.'])}
                        <DatePicker addonBefore='Date' id='date' />
                    </div>
                    <div className='alignClear' />

                    <br />

                    <div className='alignLeft'>
                        {this.renderInputList(['Hire rate', 'Apprx. dur', 'Ballast bonus', 'Commisision'])}
                        <br />
                        <br />
                        <p>Ballast distance</p>
                        {this.renderInputList(['Non Seca', 'Seca', 'Ifo price', 'Mdo price'])}

                    </div>
                    <div className='alignRight'>
                        <p>Port costs</p>
                        {this.renderInputList(['Delivery', 'Redelivery'])}
                        <br />
                        <br />
                        {this.renderInputList(['Canals', 'Miscel.', 'Lost/waiting days'])}
                    </div>
                    <div className='alignClear' />

                    <br />
                    <br />
                </div>

                <div className='alignRight'>
                    <div className='alignLeft' >
                        {this.renderInputList(['Speed', 'Ifo Ballast', 'Ifo Laden', 'Mdo Sea', 'Mdo port', 'Streaming'])}
                    </div>
                    <div className='alignRight'>
                        {this.renderInputListDisabled(['Gross revenue', 'Bunker cost', 'Expenses', 'Net revenue'], 'alignResultDarkBlue')}
                        {this.renderInputListDisabled(['Sensitivity $100', 'Sensitivity 5 days', '$5.000 bb Gross', 'Total duration'], 'alignResultGreen')}
                        
                        <Input 
                            className='alignResultRed' 
                            addonBefore='Time charter rate' 
                            name='time_charter_rate'
                            disabled={true}
                            value={this.state.tcest.time_charter_rate}/> 
                    </div>
                    <div className='alignClear' />
                </div>

                <div className='alignClear' />
            </div>
			);
	}
}
export default withRouter(TCEstimate);



