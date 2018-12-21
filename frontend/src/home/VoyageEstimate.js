import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Menu.css';
import { renderID } from '../util/Helpers';
import { 
    Radio,
    Input,
    DatePicker,
    Select,
    Button
} from 'antd';

const Option = Select.Option;

class VoyageEstimate extends Component {
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

    handleChangeRadio = (e) => {
        const id = e.target.name;
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


    renderRadioList(fields, id, style={}, className='alignRadioGroup'){
        const RadioGroup = Radio.Group;
        return (
            <RadioGroup 
                className={className} 
                name={id}
                onChange={this.handleChangeRadio.bind(this)}>
                    {fields.map( (field) => (
                        <Radio  
                            style={style} 
                            value={renderID(field)}>
                                {field}
                        </Radio> 
                    ))}
            </RadioGroup>
        )
    }
	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }

        const veerticalRadioStyle = {
            display: 'block',
            height: '30px',
            lineHeight: '30px',
        };

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
                    { voyage }
                    <br />
                    <div className='alignLeft'>
                        {this.renderInputList(['Account', 'Commodity', 'Broker', 'Laycan', 'Quantity', 'Freight rate'])}
                        <br />
                        {this.renderRadioList(['LUMPSUM', 'Per MT Intake', 'Per LT Intake'], '3bradio', veerticalRadioStyle)}
                    </div>
                    <div className='alignRight'>
                        <div className='alignLeftRadio' >
                            <Input className='alignComponent' addonBefore='L/Rate' id='lrate' onChange={this.handleChangeInput}/>
                            <Input className='alignComponent' addonBefore='D/Rate' id='drate' onChange={this.handleChangeInput}/>
                        </div>
                        <div className='alignRightRadio' >
                             {this.renderRadioList(['X','C'],'lrateRadio')}
                             <br />
                             {this.renderRadioList(['X','C'],'drateRadio')}
                        </div>
                        <div className='alignClear' />
                        {this.renderInputList(['Comm.', 'Repos.'])}
                        <DatePicker addonBefore='Date' id='date' />
                    </div>
                    <div className='alignClear' />

                    <br />

                    <div className='alignLeft'>
                        <p>Ballast distance</p>
                        {this.renderInputList(['NON Seca (Ballast)', 'Seca (Ballast)'])}
                        <br />
                        <br />
                        <p>Laden distance</p>
                        {this.renderInputList(['NON Seca (Laden)', 'Seca (Laden)', 'Lfo price', 'Mdo price', 'Lost/waiting days'])}
                    </div>
                    <div className='alignRight'>
                        <p>Port costs</p>
                        {this.renderInputList(['Load', 'Disch', 'Others', 'Canals', 'Taxes %', 'Miscel.', 'Exins'])}
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
                    <br />
                    <br />
                    <div className='alignLeft'>
                        {this.renderInputList(['Speed', 'Ifo Ballast', 'Ifo Laden', 'Mdo Sea', 'IFO port idle', 'IFO port work', 'MGO port idle', 'MGO port work', 'Boiler port'])}
                    </div>
                    <div className='alignRight'>
                        {this.renderInputList(['Load port', 'Disch. port', 'Streaming margin'])}
                        <br />
                        Days 
                        <br />
                        {this.renderInputList(['Streaming','Load days','Disch days', 'SHEX load', 'SHEX disch'], 'alignResultLightBlue', true)}
                        <Input 
                            className='alignResultRed' 
                            addonBefore='Total duration'
                            id='totalduration'
                            disabled={true}
                            value={this.state.totalduration}/> 
                    </div>
                    <div className='alignClear' />

                    <p>RESULTS</p>
                    {this.renderInputList(['Gross revenue', 'Sailing bunkers', 'Loadport bunkers', 'Disport bunkers', 'Total bunker cost', 'Expenses', 'Commissions', 'Taxes', 'Exins', 'Net Revenue'], 'alignResultDarkBlue', true)}
                    <Input 
                        className='alignResultRed' 
                        addonBefore='Time charter rate' 
                        id='timecharterrate'
                        disabled={true}
                        value={this.state.timecharterrate}/> 
                    <Input 
                        className='alignResultGreen' 
                        addonBefore='Sensitivity +/- $1'
                        id='sensitivity1'
                        disabled={true}
                        value={this.state.sensitivity1}/> 
                        
                </div>

                <div className='alignClear' />

            </div>
			);
	}
}
export default withRouter(VoyageEstimate);



