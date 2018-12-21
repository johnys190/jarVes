import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Menu.css';
import { renderID } from '../util/Helpers';
import {
    Input,
    DatePicker,
    Select,
    Button
} from 'antd';

const Option = Select.Option;

class TCEstimate extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            new: false
        };
    }

    calculate(){
        if (this.state.hirerate && this.state.ballastbonus){
            this.setState({
                timecharterrate:  Number(this.state.hirerate) + Number(this.state.ballastbonus)
            })
        }
    }

    handleChangeInput = (e) => {
        const id = e.target.id;
        const value = e.target.value;
        this.setState({
            [id]: value
            },
            this.calculate
        );
    }

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

    renderInputList(fields, className='alignComponent', disabled=false){
        return fields.map( (field) => (
             <Input 
                type='number' 
                className={className}
                addonBefore={field} 
                id={renderID(field)}
                onChange={this.handleChangeInput.bind(this)}
                disabled={disabled}/> 
          
        ))
    }



	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }

        const voyage = this.state.new ? 
                    (<div><Input addonBefore='Voyage' id='voyage' onChange={this.handleChangeInput}/><br /></div>) :
                    (<div>
                        <span class="ant-input-group-wrapper">
                            <span class="ant-input-wrapper ant-input-group">
                                <span class="ant-input-group-addon">
                                    Voyage
                                </span>
                                <Select
                                    className='alignSelect'
                                    showSearch
                                    placeholder="Select a Voyage"
                                    optionFilterProp="children"
                                    onChange={this.handleChangeSelect}
                                    onSelect={this.handleChangeSelect}
                                    filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
                                        <Option value="Jack">Jack</Option>
                                        <Option value="Lucy">Lucy</Option>
                                        <Option value="Tom">Tom</Option>
                                </Select>
                            </span>
                        </span>
                        <Input addonBefore='Save as' id='voyage' value={this.state.voyage}  onChange={this.handleChangeInput}/>
                    </div>);

        const newClearButton = this.state.new ?
                    (<Button className='button' type='danger' onClick={this.handleNewCancelClick}>Cancel</Button>) :
                    (<Button className='button' type='danger' onClick={this.handleNewCancelClick}>New</Button>);
		return (
            <div>

                <div className='alignLeft'>
                    <span class="ant-input-group-wrapper">
                        <span class="ant-input-wrapper ant-input-group">
                            <span class="ant-input-group-addon">
                                Name
                            </span>
                            <Select
                                className='alignSelect'
                                showSearch
                                placeholder="Select a ship"
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
                    
                    { voyage }
                    <br />
                    <div className='alignLeft'>
                        {this.renderInputList(['Account', 'Commodity', 'Broker'])}
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
                    <div className='alignCenter'>
                        <Button className='button' type='primary' onClick={this.handleSave}>Save</Button>
                    </div>
                    <div className='alignCenter'>
                        {newClearButton}
                    </div>
                    <div className='alignClear' />
                </div>

                <div className='alignRight'>
                    <div className='alignLeft' >
                        {this.renderInputList(['Speed', 'Ifo Ballast', 'Ifo Laden', 'Mdo Sea', 'Mdo port', 'Streaming'])}
                    </div>
                    <div className='alignRight'>
                        {this.renderInputList(['Gross revenue', 'Bunker cost', 'Expenses', 'Net revenue'], 'alignResultDarkBlue', true)}
                        {this.renderInputList(['Sensitivity $100', 'Sensitivity 5 days', '$5.000 bb Gross', 'Total duration'], 'alignResultGreen', true)}
                        
                        <Input 
                            className='alignResultRed' 
                            addonBefore='Time charter rate' 
                            id='timecharterrate'
                            disabled={true}
                            value={this.state.timecharterrate}/> 
                    </div>
                    <div className='alignClear' />
                </div>

                <div className='alignClear' />
            </div>
			);
	}
}
export default withRouter(TCEstimate);



